package dici.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;

public class DataRealEstateToDatabase {
	private final int INDEX_REAL_SURFACE_BATI  = 31;
	private final int INDEX_CITY_CODE          = 10;
	private final int INDEX_PRICE              = 4;
	private final int INDEX_NATURE_TRANSACTION = 3;
	
	private String csvFilePath;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private String dbName;
	private Map<String, List<Float>> pricesM2Cities = new HashMap<>();

	public DataRealEstateToDatabase() {
		loadProperties();
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try (FileInputStream inputStream = new FileInputStream("../../../../resources/application.properties")) {
			properties.load(inputStream);
			this.csvFilePath = properties.getProperty("csv.file.path");
			this.dbUrl = properties.getProperty("db.url");
			this.dbUser = properties.getProperty("db.user");
			this.dbPassword = properties.getProperty("db.password");
			this.dbName = properties.getProperty("db.name");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processCSVAndInsertData() {
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			connection.setAutoCommit(false);

			String[] headers = readHeader(br);

			if (headers == null) return;

			int valeurFonciereIndex = findValeurFonciereIndex(headers);
			if (valeurFonciereIndex == -1) {
				System.err.println("Colonne 'VALEUR_FONCIERE' introuvable.");
				return;
			};

			String insertQuery = "INSERT INTO " + dbName + "(prix_m2) VALUES (?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				readAndInsertData(br, preparedStatement, valeurFonciereIndex);
				preparedStatement.executeBatch();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	private String[] readHeader(BufferedReader br) throws IOException {
		String headerLine = br.readLine();
		if (headerLine == null)
			return null;

		return headerLine.split(",");
	}

	private int findValeurFonciereIndex(String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			if ("VALEUR_FONCIERE".equalsIgnoreCase(headers[i])) {
				return i;
			}
		}
		return -1;
	}

	private void readAndInsertData(BufferedReader br, PreparedStatement preparedStatement, int valeurFonciereIndex) throws IOException, SQLException {
		String line;

		while ((line = br.readLine()) != null) {
			
			analyseData(line);
			
			String[] data = line.split(",");

			if (valeurFonciereIndex < data.length) {
				Float value = data[valeurFonciereIndex] == null || data[valeurFonciereIndex].isEmpty() ? 0 : Float.parseFloat(data[valeurFonciereIndex]);

				preparedStatement.setFloat(1, value);
				preparedStatement.addBatch();
			}
		}
	}

	private void analyseData ( String line ) {
		String[] data = line.split ( "," );
		String cityCode = data[INDEX_CITY_CODE];

		if ( ! data[INDEX_NATURE_TRANSACTION].equals ( "Vente" ) || data[INDEX_REAL_SURFACE_BATI].isEmpty ( ) )
			return;
		
		Float priceM2 = Float.parseFloat ( data[INDEX_REAL_SURFACE_BATI] ) / Float.parseFloat ( data[INDEX_PRICE] );
		pricesM2Cities.computeIfAbsent ( cityCode, k -> new ArrayList<> ( ) ).add ( priceM2 );
		System.out.println("City code: " + cityCode + " Price m2: " + priceM2);
	}

	public static void main(String[] args) {
		DataRealEstateToDatabase csvToDatabase = new DataRealEstateToDatabase();
		csvToDatabase.processCSVAndInsertData();
	}
}