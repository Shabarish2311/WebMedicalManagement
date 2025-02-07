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
import model.Bill;

@WebServlet("/ViewBillsServlet")
public class ViewBillsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bill> bills = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM bills ORDER BY bill_date DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bills.add(bill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("bills", bills);
        request.getRequestDispatcher("viewBills.jsp").forward(request, response);
    }
}
