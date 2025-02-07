package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBUtility.DBConnection;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect("profile.jsp?error=Passwords do not match");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Verify current password
            String sql = "SELECT password FROM users WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        if (!storedPassword.equals(currentPassword)) {
                            response.sendRedirect("profile.jsp?error=Incorrect current password");
                            return;
                        }
                    } else {
                        response.sendRedirect("profile.jsp?error=User not found");
                        return;
                    }
                }
            }

            // Update password
            sql = "UPDATE users SET password = ? WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPassword);
                ps.setInt(2, userId);
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("profile.jsp?success=Password changed successfully");
                } else {
                    response.sendRedirect("profile.jsp?error=Failed to update password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("profile.jsp?error=Database error");
        }
    }
}
