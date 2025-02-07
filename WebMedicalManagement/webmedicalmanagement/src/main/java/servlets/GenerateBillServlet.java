package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBUtility.DBConnection;
import model.BillItem;
import model.Medicine;

@WebServlet("/GenerateBillServlet")
public class GenerateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET request to fetch medicine list and display bill items
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Fetch the list of medicines from the database
        List<Medicine> medicines = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM medicines");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineId(rs.getInt("medicine_id"));
                medicine.setName(rs.getString("name"));
                medicine.setPrice(rs.getDouble("price"));
                medicines.add(medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set medicines list as request attribute
        request.setAttribute("medicines", medicines);

        // Forward to JSP page to display medicines and bill items
        request.getRequestDispatcher("generateBill.jsp").forward(request, response);
    }

    // Handle POST request for adding items and generating bills
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer pharmacistId = (Integer) session.getAttribute("user_id");

        // Redirect if not logged in
        if (pharmacistId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("addItem".equals(action)) {
            int medicineId = Integer.parseInt(request.getParameter("medicineId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double pricePerUnit = 0.0;
            String medicineName = "";

            // Fetch medicine details from DB
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT name, price FROM medicines WHERE medicine_id = ?")) {

                ps.setInt(1, medicineId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        medicineName = rs.getString("name");
                        pricePerUnit = rs.getDouble("price");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Get bill items from session
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            if (billItems == null) {
                billItems = new ArrayList<>();
            }

            // Check if medicine already exists, update quantity if found
            boolean itemExists = false;
            for (BillItem item : billItems) {
                if (item.getMedicineId() == medicineId) {
                    item.setQuantity(item.getQuantity() + quantity);
                    itemExists = true;
                    break;
                }
            }

            // If medicine not found, add new entry
            if (!itemExists) {
                BillItem billItem = new BillItem();
                billItem.setMedicineId(medicineId);
                billItem.setMedicineName(medicineName);
                billItem.setQuantity(quantity);
                billItem.setPricePerUnit(pricePerUnit);
                billItems.add(billItem);
            }

            // Update session
            session.setAttribute("billItems", billItems);

            // Redirect to refresh page and update UI
            response.sendRedirect("GenerateBillServlet");
        } 
        else if ("generateBill".equals(action)) {
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            if (billItems == null || billItems.isEmpty()) {
                response.sendRedirect("GenerateBillServlet?error=No items added");
                return;
            }

            double totalAmount = billItems.stream().mapToDouble(item -> item.getQuantity() * item.getPricePerUnit()).sum();
            Timestamp billDate = new Timestamp(System.currentTimeMillis());

            int billId = 0;
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false); // Start transaction

                // Insert bill details
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO bills (total_amount, pharmacist_id, bill_date) VALUES (?, ?, ?)", 
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setDouble(1, totalAmount);
                    ps.setInt(2, pharmacistId);
                    ps.setTimestamp(3, billDate);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            billId = rs.getInt(1);
                        }
                    }
                }

                // Insert items into bill_items table
                try (PreparedStatement itemPs = conn.prepareStatement(
                        "INSERT INTO bill_items (bill_id, medicine_id, medicineName, quantity, price_per_unit) VALUES (?, ?, ?, ?, ?)")) {

                    for (BillItem item : billItems) {
                        itemPs.setInt(1, billId);
                        itemPs.setInt(2, item.getMedicineId());
                        itemPs.setString(3, item.getMedicineName());
                        itemPs.setInt(4, item.getQuantity());
                        itemPs.setDouble(5, item.getPricePerUnit());
                        itemPs.addBatch();
                    }
                    itemPs.executeBatch();
                }

                // Insert into sales table
                try (PreparedStatement salesPs = conn.prepareStatement(
                        "INSERT INTO sales (medicine_id, quantity, total_price, sale_date) VALUES (?, ?, ?, ?)")) {

                    for (BillItem item : billItems) {
                        salesPs.setInt(1, item.getMedicineId());
                        salesPs.setInt(2, item.getQuantity());
                        salesPs.setDouble(3, item.getQuantity() * item.getPricePerUnit());
                        salesPs.setTimestamp(4, billDate);
                        salesPs.addBatch();
                    }
                    salesPs.executeBatch();
                }

                // Update stock after generating bill
                try (PreparedStatement stockPs = conn.prepareStatement(
                        "UPDATE medicines SET stock = stock - ? WHERE medicine_id = ?")) {

                    for (BillItem item : billItems) {
                        stockPs.setInt(1, item.getQuantity()); // Subtract the quantity
                        stockPs.setInt(2, item.getMedicineId());
                        stockPs.addBatch();
                    }
                    stockPs.executeBatch();
                }

                conn.commit(); // Commit transaction
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Clear session after bill is generated
            session.removeAttribute("billItems");
            response.sendRedirect("ViewBillsServlet");
        }
    }
}
