package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBUtility.DBConnection;
import model.BillItem;

@WebServlet("/ShowBillServlet")
public class ShowBillServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId"));
        List<BillItem> billItems = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT m.name, bi.quantity, bi.price_per_unit FROM bill_items bi JOIN medicines m ON bi.medicine_id = m.medicine_id WHERE bi.bill_id = ?")) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setMedicineName(rs.getString("name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPricePerUnit(rs.getDouble("price_per_unit"));
                billItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("billItems", billItems);
        request.getRequestDispatcher("showBill.jsp").forward(request, response);
    }
	
	

}
