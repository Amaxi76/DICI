package dici.models.dao;

import java.sql.*;
import java.util.List;

import dici.models.dto.DICI;

import java.util.ArrayList;

public class DICIDao {
	
	// Méthode pour ajouter un enregistrement
	public static void add ( Connection connection, DICI dici ) throws SQLException {
		String sql = "INSERT INTO dici (code_ville, nom_ville, prix_m2, type_zone, age, niveau_diplome, densite_pop, pop_active, revenu_median, taux_chomage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, dici.getCodeVille());
			stmt.setString(2, dici.getNomVille());
			stmt.setInt(3, dici.getPrixM2());
			stmt.setString(4, dici.getTypeZone());
			stmt.setInt(5, dici.getAge());
			stmt.setString(6, dici.getNiveauDiplome());
			stmt.setInt(7, dici.getDensitePop());
			stmt.setInt(8, dici.getPopActive());
			stmt.setInt(9, dici.getRevenuMedian());
			stmt.setInt(10, dici.getTauxChomage());
			stmt.executeUpdate();
		}
	}

	// Méthode pour récupérer un enregistrement par ID
	public static DICI get ( Connection connection, int id ) throws SQLException {
		String sql = "SELECT * FROM dici WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new DICI(
						rs.getInt("id"),
						rs.getInt("code_ville"),
						rs.getString("nom_ville"),
						rs.getInt("prix_m2"),
						rs.getString("type_zone"),
						rs.getInt("age"),
						rs.getString("niveau_diplome"),
						rs.getInt("densite_pop"),
						rs.getInt("pop_active"),
						rs.getInt("revenu_median"),
						rs.getInt("taux_chomage")
					);
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves a DICI object from the database based on the city name.
	 *
	 * @param connection the database connection to be used for the query
	 * @param nomVille the name of the city to search for
	 * @return a DICI object if a matching city is found, otherwise null
	 * @throws SQLException if a database access error occurs
	 */
	public static DICI getByCityName ( Connection connection, String cityName ) throws SQLException {
		String requete = "SELECT * FROM dici WHERE nom_ville = ?";

		try ( PreparedStatement stmt = connection.prepareStatement ( requete ) ) {
			stmt.setString ( 1, cityName );

			try ( ResultSet rs = stmt.executeQuery ( ) ) {
				if ( rs.next ( ) ) {
					return new DICI (
						rs.getInt    ( "id"            ),
						rs.getInt    ( "code_ville"    ),
						rs.getString ( "nom_ville"     ),
						rs.getInt    ( "prix_m2"       ),
						rs.getString ( "type_zone"     ),
						rs.getInt    ( "age"           ),
						rs.getString ( "niveau_diplome"),
						rs.getInt    ( "densite_pop"   ),
						rs.getInt    ( "pop_active"    ),
						rs.getInt    ( "revenu_median" ),
						rs.getInt    ( "taux_chomage"  )
					);
				}
			}
		}

		return null;
	}

	// Méthode pour récupérer tous les enregistrements
	public static List<DICI> getAll ( Connection connection ) throws SQLException {
		List<DICI> dicis = new ArrayList<>();
		String sql = "SELECT * FROM dici";
		try (Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				dicis.add(new DICI(
					rs.getInt("id"),
					rs.getInt("code_ville"),
					rs.getString("nom_ville"),
					rs.getInt("prix_m2"),
					rs.getString("type_zone"),
					rs.getInt("age"),
					rs.getString("niveau_diplome"),
					rs.getInt("densite_pop"),
					rs.getInt("pop_active"),
					rs.getInt("revenu_median"),
					rs.getInt("taux_chomage")
				));
			}
		}
		return dicis;
	}

	// Méthode pour mettre à jour un enregistrement
	public static void update ( Connection connection, DICI dici ) throws SQLException {
		String sql = "UPDATE dici SET code_ville = ?, nom_ville = ?, prix_m2 = ?, type_zone = ?, age = ?, niveau_diplome = ?, densite_pop = ?, pop_active = ?, revenu_median = ?, taux_chomage = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, dici.getCodeVille());
			stmt.setString(2, dici.getNomVille());
			stmt.setInt(3, dici.getPrixM2());
			stmt.setString(4, dici.getTypeZone());
			stmt.setInt(5, dici.getAge());
			stmt.setString(6, dici.getNiveauDiplome());
			stmt.setInt(7, dici.getDensitePop());
			stmt.setInt(8, dici.getPopActive());
			stmt.setInt(9, dici.getRevenuMedian());
			stmt.setInt(10, dici.getTauxChomage());
			stmt.setInt(11, dici.getId());
			stmt.executeUpdate();
		}
	}

}
