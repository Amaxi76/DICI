package dici.models.dao;
import java.sql.*;
import java.util.List;

import dici.models.dto.Donnees_demo;

import java.util.ArrayList;

public class Donnees_demoDAO {
	
	private Connection connection;
	
	public Donnees_demoDAO(Connection connection) {
		this.connection = connection;
	}
	
	// Méthode pour ajouter un enregistrement
	public void add(Donnees_demo donnees_demo) throws SQLException {
		String sql = "INSERT INTO donnees_demo (age, niveau_diplome, densite_pop, pop_active, revenu_median, taux_chomage) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, donnees_demo.getAge());
			stmt.setString(2, donnees_demo.getNiveauDiplome());
			stmt.setInt(3, donnees_demo.getDensitePop());
			stmt.setInt(4, donnees_demo.getPopActive());
			stmt.setInt(5, donnees_demo.getRevenuMedian());
			stmt.setInt(6, donnees_demo.getTauxChomage());
			stmt.executeUpdate();
		}
	}
	
	// Méthode pour récupérer un enregistrement par ID
	public Donnees_demo get(int id) throws SQLException {
		String sql = "SELECT * FROM donnees_demo WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Donnees_demo(
						rs.getInt("id"),
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
	
	// Méthode pour récupérer tous les enregistrements
	public List<Donnees_demo> getAll() throws SQLException {
		List<Donnees_demo> donnees_demos = new ArrayList<>();
		String sql = "SELECT * FROM donnees_demo";
		try (Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				donnees_demos.add(new Donnees_demo(
					rs.getInt("id"),
					rs.getInt("age"),
					rs.getString("niveau_diplome"),
					rs.getInt("densite_pop"),
					rs.getInt("pop_active"),
					rs.getInt("revenu_median"),
					rs.getInt("taux_chomage")
				));
			}
		}
		return donnees_demos;
	}

	// Méthode pour mettre à jour un enregistrement
	public void update(Donnees_demo donnees_demo) throws SQLException {
		String sql = "UPDATE donnees_demo SET age = ?, niveau_diplome = ?, densite_pop = ?, pop_active = ?, revenu_median = ?, taux_chomage = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, donnees_demo.getAge());
			stmt.setString(2, donnees_demo.getNiveauDiplome());
			stmt.setInt(3, donnees_demo.getDensitePop());
			stmt.setInt(4, donnees_demo.getPopActive());
			stmt.setInt(5, donnees_demo.getRevenuMedian());
			stmt.setInt(6, donnees_demo.getTauxChomage());
			stmt.setInt(7, donnees_demo.getId());
			stmt.executeUpdate();
		}
	}

	// Méthode pour supprimer un enregistrement par ID
	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM donnees_demo WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
}