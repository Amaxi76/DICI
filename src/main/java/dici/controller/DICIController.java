package dici.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import dici.models.dto.DICI;
import java.io.InputStream;

public class DICIController {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private List<DICI> diciList;

	public DICIController() {
		loadProperties();
		loadData();
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try (FileInputStream inputStream = new FileInputStream("src/resources/application.properties")) {
			properties.load(inputStream);
			this.dbUrl = properties.getProperty("db.url");
			this.dbUser = properties.getProperty("db.user");
			this.dbPassword = properties.getProperty("db.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadData() {
		this.diciList = new ArrayList<>();
		String query = "SELECT code_ville, nom_ville, prix_m2, type_zone, age, niveau_diplome, densite_pop, pop_active, revenu_median, taux_chomage FROM DICI";

		try {
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				DICI dici = new DICI(
					resultSet.getInt("code_ville"),
					resultSet.getString("nom_ville"),
					resultSet.getInt("prix_m2"),
					resultSet.getString("type_zone"),
					resultSet.getInt("age"),
					resultSet.getString("niveau_diplome"),
					resultSet.getInt("densite_pop"),
					resultSet.getInt("pop_active"),
					resultSet.getInt("revenu_median"),
					resultSet.getInt("taux_chomage")
				);

				this.diciList.add(dici);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<DICI> getDiciList() { return this.diciList; }

	public static void main(String[] args) {
		DICIController DICIController = new DICIController();
	}
}