package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBUtility.DBConnection;
import dao.BillDAO;
import model.Bill;
import model.BillItem;

public class BillDAOImpl implements BillDAO {

    @Override
    public int createBill(Bill bill) {
        String query = "INSERT INTO Bills (pharmacist_id, total_amount, bill_date, pdf_path) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bill.getPharmacistId());
            ps.setDouble(2, bill.getTotalAmount());
            ps.setTimestamp(3, bill.getBillDate());
            ps.setString(4, bill.getPdfPath());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return generated Bill ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean addBillItem(BillItem billItem) {
        String query = "INSERT INTO BillItems (bill_id, medicine_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, billItem.getBillId());
            ps.setInt(2, billItem.getMedicineId());
            ps.setInt(3, billItem.getQuantity());
            ps.setDouble(4, billItem.getPricePerUnit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Bill> getAllBills() {
        String query = "SELECT * FROM Bills";
        List<Bill> bills = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setPharmacistId(rs.getInt("pharmacist_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setPdfPath(rs.getString("pdf_path"));
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public Bill getBillById(int billId) {
        String query = "SELECT * FROM Bills WHERE bill_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setPharmacistId(rs.getInt("pharmacist_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setPdfPath(rs.getString("pdf_path"));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BillItem> getBillItemsByBillId(int billId) {
        String query = "SELECT * FROM BillItems WHERE bill_id = ?";
        List<BillItem> billItems = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BillItem billItem = new BillItem();
                billItem.setItemId(rs.getInt("item_id"));
                billItem.setBillId(rs.getInt("bill_id"));
                billItem.setMedicineId(rs.getInt("medicine_id"));
                billItem.setQuantity(rs.getInt("quantity"));
                billItem.setPricePerUnit(rs.getDouble("price_per_unit"));
                billItems.add(billItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billItems;
    }
}
