package dici.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataDemographyToDatabase {
	private String csvFilePath;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private String dbName;

	public DataDemographyToDatabase() {
		loadProperties();
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
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

			if (headers == null)
				return;

			int valeurFonciereIndex = -1;
			for (int i = 0; i < headers.length; i++) {
				if ("VALEUR_FONCIERE".equalsIgnoreCase(headers[i])) {
					valeurFonciereIndex = i;
					break;
				}
			}

			if (valeurFonciereIndex == -1) {
				System.err.println("Colonne 'VALEUR_FONCIERE' introuvable.");
				return;
			}

			String insertQuery = "INSERT INTO " + dbName + "(VALEUR_FONCIERE) VALUES (?)";

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

		return headerLine.split(";");
	}

	private void readAndInsertData(BufferedReader br, PreparedStatement preparedStatement, int valeurFonciereIndex) throws IOException, SQLException {
		String line;

		while ((line = br.readLine()) != null) {
			String[] data = line.split(";");

			if (valeurFonciereIndex < data.length) {
				preparedStatement.setString(1, data[valeurFonciereIndex]);
				preparedStatement.addBatch();
			}
		}
	}

	public static void main(String[] args) {
		DataDemographyToDatabase csvToDatabase = new DataDemographyToDatabase();
		csvToDatabase.processCSVAndInsertData();
	}
}