package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtility.DBConnection;
import dao.SalesDAO;
import model.Sale;

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean addSale(Sale sale) {
        String query = "INSERT INTO Sales (medicine_id, quantity, total_price, sale_date) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, sale.getMedicine_id());
            ps.setInt(2, sale.getQuantity());
            ps.setDouble(3, sale.getTotal_price());
            ps.setTimestamp(4, sale.getSale_date());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Sale> getAllSales() {
        String query = "SELECT * FROM Sales";
        List<Sale> sales = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSale_id(rs.getInt("sale_id"));
                sale.setMedicine_id(rs.getInt("medicine_id"));
                sale.setQuantity(rs.getInt("quantity"));
                sale.setTotal_price(rs.getDouble("total_price"));
                sale.setSale_date(rs.getTimestamp("sale_date"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    @Override
    public List<Sale> getSalesByMedicineId(int medicineId) {
        String query = "SELECT * FROM Sales WHERE medicine_id = ?";
        List<Sale> sales = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, medicineId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSale_id(rs.getInt("sale_id"));
                sale.setMedicine_id(rs.getInt("medicine_id"));
                sale.setQuantity(rs.getInt("quantity"));
                sale.setTotal_price(rs.getDouble("total_price"));
                sale.setSale_date(rs.getTimestamp("sale_date"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    @Override
    public double getTotalSalesAmount() {
        String query = "SELECT SUM(total_price) AS total_sales FROM Sales";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total_sales");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
