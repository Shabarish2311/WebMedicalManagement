package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBUtility.DBConnection;

@WebServlet("/AddMedicineServlet")
public class AddMedicineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String medicineName = request.getParameter("medicineName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String expiryDate = request.getParameter("expiryDate");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO medicines (name, category, description, price, quantity, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medicineName);
            pstmt.setString(2, category);
            pstmt.setString(3, description);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, quantity);
            pstmt.setString(6, expiryDate);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("dashboard.jsp?message=Medicine added successfully");
            } else {
                response.sendRedirect("addMedicine.jsp?error=Failed to add medicine");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addMedicine.jsp?error=Database error");
        }
    }
}

