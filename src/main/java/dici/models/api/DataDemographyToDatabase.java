package dici.models.api;

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
		try (FileInputStream inputStream = new FileInputStream("src/resources/application.properties")) {
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
		try (BufferedReader br = new BufferedReader(new FileReader(this.csvFilePath));
			 Connection connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword)) {

			connection.setAutoCommit(false);

			String[] headers = br.readLine().split(",");
			int codeVilleIndex = findIndex(headers, "CODGEO");

			String insertQuery = "INSERT INTO " + dbName + " (code_ville, nom_ville, prix_m2, age, niveau_diplome, densite_pop, pop_active, taux_chomage) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] data = line.split(",");


					String codeVille = String.format ( "%05d", Integer.parseInt ( data[codeVilleIndex] ) );
					String nomVille = APITools.getCityName(data[codeVilleIndex]);
					// System.out.println("Processing city: " + nomVille + " (Code: " + codeVille + ")");
					// float prixM2 = APIToolbox.getPriceM2(data[codeVilleIndex]);
					float prixM2 = 0;
					int age = calculateAge(data, headers);
					int niveauDiplome = calculateNiveauDiplome(data, headers);
					int densitePop = calculateDensitePop(data, headers);
					int popActive = calculatePopActive(data, headers);
					int tauxChomage = calculateTauxChomage(data, headers);

					if ( nomVille == null )
						continue;

					preparedStatement.setInt(1, Integer.parseInt(codeVille));
					preparedStatement.setString(2, nomVille);
					preparedStatement.setFloat(3, prixM2);
					preparedStatement.setInt(4, age);
					preparedStatement.setInt(5, niveauDiplome);
					preparedStatement.setInt(6, densitePop);
					preparedStatement.setInt(7, popActive);
					preparedStatement.setInt(8, tauxChomage);

					preparedStatement.addBatch();
				}

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

	private int findIndex(String[] headers, String columnName) {
		for (int i = 0; i < headers.length; i++) {
			if (columnName.equalsIgnoreCase(headers[i])) {
				return i;
			}
		}
		return -1;
	}

	private int calculateAge(String[] data, String[] headers) {
		int age0_14Index = findIndex(headers, "P21_POP0014");
		int age15_29Index = findIndex(headers, "P21_POP1529");
		int age30_44Index = findIndex(headers, "P21_POP3044");
		int age45_59Index = findIndex(headers, "P21_POP4559");
		int age60_74Index = findIndex(headers, "P21_POP6074");
		int age75_89Index = findIndex(headers, "P21_POP7589");
		int age90PlusIndex = findIndex(headers, "P21_POP90P");

		float age0_14 = Float.parseFloat(data[age0_14Index]);
		float age15_29 = Float.parseFloat(data[age15_29Index]);
		float age30_44 = Float.parseFloat(data[age30_44Index]);
		float age45_59 = Float.parseFloat(data[age45_59Index]);
		float age60_74 = Float.parseFloat(data[age60_74Index]);
		float age75_89 = Float.parseFloat(data[age75_89Index]);
		float age90Plus = Float.parseFloat(data[age90PlusIndex]);

		float totalPopulation = age0_14 + age15_29 + age30_44 + age45_59 + age60_74 + age75_89 + age90Plus;

		return totalPopulation == 0 ? 0 : (int) ((age0_14 * 7 + age15_29 * 22 + age30_44 * 37 + age45_59 * 52 + age60_74 * 67 + age75_89 * 82 + age90Plus * 95) / totalPopulation);
	}

	private int calculateNiveauDiplome(String[] data, String[] headers) {
		int diplomeSup2Index = findIndex(headers, "P21_NSCOL15P_SUP2");
		int diplomeBacIndex = findIndex(headers, "P21_NSCOL15P");
		int diplomeSup34Index = findIndex(headers, "P21_NSCOL15P_SUP34");
		int diplomeSup5Index = findIndex(headers, "P21_NSCOL15P_SUP5");
		int populationIndex = findIndex(headers, "C21_POP15P");

		float diplomeSup2 = Float.parseFloat(data[diplomeSup2Index]);
		float diplomeSup34 = Float.parseFloat(data[diplomeSup34Index]);
		float diplomeSup5 = Float.parseFloat(data[diplomeSup5Index]);
		float population = Float.parseFloat(data[populationIndex]);
		float diplomeBac = Float.parseFloat(data[diplomeBacIndex]);

		return population == 0 ? 0 : (int) ((diplomeBac + diplomeSup2 * 2 + diplomeSup34 * 3.5 + diplomeSup5 * 5) / population);
	}

	private int calculateDensitePop(String[] data, String[] headers) {
		int populationIndex = findIndex(headers, "C21_POP15P");
		int superficieIndex = findIndex(headers, "SUPERF");

		float population = Float.parseFloat(data[populationIndex]);
		float superficie = Float.parseFloat(data[superficieIndex]);

		return superficie == 0 ? 0 : (int) (population / superficie);
	}

	private int calculatePopActive(String[] data, String[] headers) {
		int populationIndex = findIndex(headers, "C21_POP15P");
		int retraiteIndex = findIndex(headers, "C21_POP15P_CS7");
		int chomageIndex = findIndex(headers, "C21_POP15P_CS8");

		float population = Float.parseFloat(data[populationIndex]);
		float retraite = Float.parseFloat(data[retraiteIndex]);
		float chomage = Float.parseFloat(data[chomageIndex]);

		return population == 0 ? 0 : (int) (((population - retraite - chomage) / population) * 100);
	}

	private int calculateTauxChomage(String[] data, String[] headers) {
		int populationIndex = findIndex(headers, "C21_POP15P");
		int chomageIndex = findIndex(headers, "C21_POP15P_CS8");

		float population = Float.parseFloat(data[populationIndex]);
		float chomage = Float.parseFloat(data[chomageIndex]);

		return population == 0 ? 0 : (int) ((chomage / population) * 100);
	}

	public static void main(String[] args) {
		DataDemographyToDatabase app = new DataDemographyToDatabase();
		app.processCSVAndInsertData();
	}
}