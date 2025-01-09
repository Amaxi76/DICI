package dici.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSVToDatabase {
	private static final String CSV_FILE_PATH = "./data.csv";
	private static final String DB_URL = "jdbc:postgresql://woody/lk210125";
	private static final String DB_USER = "lk210125";
	private static final String DB_PASSWORD = "Kyliann.0Bado";
	private static final String DB_NAME = "ETUDIANT";

	public void processCSVAndInsertData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH));
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			connection.setAutoCommit(false);

			String[] headers = readHeader(br);

			if (headers == null)
				return;

			String insertQuery = createInsertQuery(headers);

			try {
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

				readAndInsertData(br, preparedStatement);

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

	private String createInsertQuery(String[] headers) {
		return "INSERT INTO" + DB_NAME + "(" + String.join(", ", headers) + ") VALUES (" +
				String.join(", ", new String[headers.length]).replace("\0", "?") + ")";
	}

	private void readAndInsertData(BufferedReader br, PreparedStatement preparedStatement) throws IOException, SQLException {
		String line;

		while ((line = br.readLine()) != null) {
			String[] data = line.split(";");

			for (int i = 0; i < data.length; i++) {
				preparedStatement.setString(i + 1, data[i]);
			}

			preparedStatement.addBatch();
		}
	}

	public static void main(String[] args) {
		CSVToDatabase csvToDatabase = new CSVToDatabase();
		csvToDatabase.processCSVAndInsertData();
	}
}