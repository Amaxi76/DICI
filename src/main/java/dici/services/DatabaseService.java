package dici.services;

import java.sql.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.io.InputStream;
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

	public static Map<String, List<String>> getCityInfoByNames(List<String> cityNames) {
		Map<String, List<String>>results = new HashMap<>();
		String placeholders = cityNames.stream().map(name -> "?").collect(Collectors.joining(", "));
		String query = "SELECT * FROM dici.dici WHERE nom_ville IN (" + placeholders + ")";

		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query)) {

			for (int i = 0; i < cityNames.size(); i++) {
				stmt.setString(i + 1, cityNames.get(i));
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String cityName = rs.getString("nom_ville");
					List<String> cityInfo = new ArrayList<>();
					cityInfo.add(rs.getString("prix_m2"));
					cityInfo.add(rs.getString("age"));
					cityInfo.add(rs.getString("niveau_diplome"));
					cityInfo.add(rs.getString("densite_pop"));
					cityInfo.add(rs.getString("pop_active"));
					cityInfo.add(rs.getString("taux_chomage"));

					results.put(cityName, cityInfo);
				}
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	public static void main(String[] args) {
		List<String> cityNames = Arrays.asList("Toulouse", "Nice", "Nantes");
		Map<String, List<String>> cityInfo = getCityInfoByNames(cityNames);

		for (Map.Entry<String, List<String>> entry : cityInfo.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}
