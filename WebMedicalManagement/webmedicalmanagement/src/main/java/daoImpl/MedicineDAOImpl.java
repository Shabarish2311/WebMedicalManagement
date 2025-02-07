package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtility.DBConnection;
import dao.MedicineDAO;
import model.Medicine;

public class MedicineDAOImpl implements MedicineDAO {

    @Override
    public boolean addMedicine(Medicine medicine) {
        String query = "INSERT INTO Medicines (name, description, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getDescription());
            ps.setDouble(3, medicine.getPrice());
            ps.setInt(4, medicine.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMedicine(Medicine medicine) {
        String query = "UPDATE Medicines SET name = ?, description = ?, price = ?, stock = ? WHERE medicine_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getDescription());
            ps.setDouble(3, medicine.getPrice());
            ps.setInt(4, medicine.getStock());
            ps.setInt(5, medicine.getMedicineId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMedicine(int medicineId) {
        String query = "DELETE FROM Medicines WHERE medicine_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Medicine getMedicineById(int medicineId) {
        String query = "SELECT * FROM Medicines WHERE medicine_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, medicineId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setName(rs.getString("name"));
                medicine.setDescription(rs.getString("description"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setStock(rs.getInt("stock"));
                return medicine;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        String query = "SELECT * FROM Medicines";
        List<Medicine> medicines = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setName(rs.getString("name"));
                medicine.setDescription(rs.getString("description"));
                medicine.setPrice(rs.getDouble("price"));
                medicine.setStock(rs.getInt("stock"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    @Override
    public boolean updateStock(int medicineId, int quantityToRemove) {
        String query = "UPDATE Medicines SET stock = stock - ? WHERE medicine_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            // Set the quantity to remove and the medicine_id
            ps.setInt(1, quantityToRemove);
            ps.setInt(2, medicineId);
            
            // Execute the update and check if the stock is updated
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
