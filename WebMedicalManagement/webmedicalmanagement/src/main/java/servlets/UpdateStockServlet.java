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

import model.Medicine;

@WebServlet("/UpdateStockServlet")
public class UpdateStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private void loadMedicines(HttpServletRequest request) {
        List<Medicine> medicines = new ArrayList<>();

        try (Connection con = DBUtility.DBConnection.getConnection()) {
            String sql = "SELECT medicine_id, name, description FROM medicines";
            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicine medicine = new Medicine();
                    medicine.setMedicineId(rs.getInt("medicine_id"));
                    medicine.setName(rs.getString("name"));
                    medicine.setDescription(rs.getString("description"));
                    medicines.add(medicine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("medicines", medicines);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadMedicines(request);
        request.getRequestDispatcher("updateStock.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int medicineId = Integer.parseInt(request.getParameter("medicineId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection con = DBUtility.DBConnection.getConnection()) {
            String sql = "UPDATE medicines SET stock = stock + ? WHERE medicine_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, quantity);
                ps.setInt(2, medicineId);
                
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    request.setAttribute("message", "Stock Updated Successfully");
                } else {
                    request.setAttribute("error", "Failed to Update Stock");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating stock");
        }

        loadMedicines(request);
        request.getRequestDispatcher("updateStock.jsp").forward(request, response);
    }
}