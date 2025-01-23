package dici.services;

import java.sql.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.io.InputStream;

public class DatabaseService {

	private static Connection getConnection() throws SQLException, IOException {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/resources/application.properties")) {
			properties.load(fis);
		}

		String dbUrl = properties.getProperty("db.url");
		String dbUser = properties.getProperty("db.user");
		String dbPassword = properties.getProperty("db.password");

		return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	}

	public static List<Map<String, Object>> getCityInfoByNames(List<String> cityNames) {
		List<Map<String, Object>> results = new ArrayList<>();
		String placeholders = cityNames.stream().map(name -> "?").collect(Collectors.joining(", "));
		String query = "SELECT * FROM dici.dici WHERE nom_ville IN (" + placeholders + ")";

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			for (int i = 0; i < cityNames.size(); i++) {
				stmt.setString(i + 1, cityNames.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> row = new HashMap<>();
					row.put("id", rs.getInt("id"));
					row.put("code_ville", rs.getInt("code_ville"));
					row.put("nom_ville", rs.getString("nom_ville"));
					row.put("prix_m2", rs.getDouble("prix_m2"));
					row.put("age", rs.getInt("age"));
					row.put("niveau_diplome", rs.getInt("niveau_diplome"));
					row.put("densite_pop", rs.getInt("densite_pop"));
					row.put("pop_active", rs.getInt("pop_active"));
					row.put("taux_chomage", rs.getInt("taux_chomage"));
					results.add(row);
				}
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	public static void main(String[] args) {
		List<String> cityNames = Arrays.asList("Toulouse", "Nice", "Nantes");
		List<Map<String, Object>> cityInfo = getCityInfoByNames(cityNames);

		for (Map<String, Object> city : cityInfo) {
			System.out.println(city);
		}
	}
}