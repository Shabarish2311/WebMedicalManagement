package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Medicine;

@WebServlet("/GenerateInventoryReportServlet")
public class GenerateInventoryReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String reportType = request.getParameter("reportType");
        List<Medicine> inventoryList = new ArrayList<>();
        
        try (Connection con = DBUtility.DBConnection.getConnection()) {
            String query = "";

            if ("all".equals(reportType)) {
                query = "SELECT * FROM medicines";
            } else if ("lowStock".equals(reportType)) {
                query = "SELECT * FROM medicines WHERE stock <= 20";
            }

            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Medicine medicine = new Medicine();
                    medicine.setMedicineId(rs.getInt("medicine_id"));
                    medicine.setName(rs.getString("name"));
                    medicine.setDescription(rs.getString("description"));
                    medicine.setPrice(rs.getDouble("price"));
                    medicine.setStock(rs.getInt("stock"));

                    inventoryList.add(medicine);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("inventoryItems", inventoryList);
        request.getRequestDispatcher("generateInventoryReport.jsp").forward(request, response);
    }
}
