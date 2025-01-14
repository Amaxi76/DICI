import java.sql.*;
import java.util.List;
import java.util.ArrayList;

// Classe DAO pour interagir avec la table "immobilier"
public class ImmobilierDAO {
    private Connection connection;

    public ImmobilierDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour ajouter un enregistrement
    public void add(Immobilier immobilier) throws SQLException {
        String sql = "INSERT INTO immobilier (nom_ville, prix_m2, type_zone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, immobilier.getNomVille());
            stmt.setInt(2, immobilier.getPrixM2());
            stmt.setString(3, immobilier.getTypeZone());
            stmt.executeUpdate();
        }
    }

    // Méthode pour récupérer un enregistrement par ID
    public Immobilier get(int id) throws SQLException {
        String sql = "SELECT * FROM immobilier WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Immobilier(
                        rs.getInt("id"),
                        rs.getString("nom_ville"),
                        rs.getInt("prix_m2"),
                        rs.getString("type_zone")
                    );
                }
            }
        }
        return null;
    }

    // Méthode pour récupérer tous les enregistrements
    public List<Immobilier> getALl() throws SQLException {
        List<Immobilier> immobiliers = new ArrayList<>();
        String sql = "SELECT * FROM immobilier";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                immobiliers.add(new Immobilier(
                    rs.getInt("id"),
                    rs.getString("nom_ville"),
                    rs.getInt("prix_m2"),
                    rs.getString("type_zone")
                ));
            }
        }
        return immobiliers;
    }

    // Méthode pour mettre à jour un enregistrement
    public void update(Immobilier immobilier) throws SQLException {
        String sql = "UPDATE immobilier SET nom_ville = ?, prix_m2 = ?, type_zone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, immobilier.getNomVille());
            stmt.setInt(2, immobilier.getPrixM2());
            stmt.setString(3, immobilier.getTypeZone());
            stmt.setInt(4, immobilier.getId());
            stmt.executeUpdate();
        }
    }

    // Méthode pour supprimer un enregistrement par ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM immobilier WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
