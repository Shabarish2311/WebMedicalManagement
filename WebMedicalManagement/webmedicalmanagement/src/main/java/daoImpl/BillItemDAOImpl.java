package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtility.DBConnection;
import dao.BillItemDAO;
import model.BillItem;

public class BillItemDAOImpl implements BillItemDAO {

    @Override
    public boolean addBillItem(BillItem billItem) {
        String query = "INSERT INTO bill_items (bill_id, medicine_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
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
    public List<BillItem> getBillItemsByBillId(int billId) {
        String query = "SELECT * FROM bill_items WHERE bill_id = ?";
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

	@Override
	public List<BillItem> getBillItems() {
	    List<BillItem> billItems = new ArrayList<>();
	    String query = "SELECT b.medicineId, b.quantity, b.pricePerUnit, m.name FROM billitems b "
	                 + "JOIN medicines m ON b.medicineId = m.medicineId";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            BillItem item = new BillItem();
	            item.setMedicineId(rs.getInt("medicineId"));
	            item.setQuantity(rs.getInt("quantity"));
	            item.setPricePerUnit(rs.getDouble("pricePerUnit"));
	            item.setMedicineName(rs.getString("name")); // Fetch medicine name
	            billItems.add(item);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return billItems;
	}

}
