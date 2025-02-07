<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.BillItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill Details</title>
    <link rel="stylesheet" href="showBills.css"> <!-- Link to the CSS file -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
    <a href="dashboard.jsp" class="back-link">Back to Dashboard</a> <!-- Back to Dashboard link outside the container -->

    <div class="container">
        <header class="page-header">
            <h1>Aster Pharmacy</h1>
        </header>

        <section class="bill-details">
            <h2>Bill Details</h2>
            <%
            List<BillItem> billItems = (List<BillItem>) request.getAttribute("billItems");
            double grandTotal = 0;
            if (billItems != null && !billItems.isEmpty()) {
            %>
                <table class="bill-table">
                    <thead>
                        <tr>
                            <th>Medicine Name</th>
                            <th>Price Per Unit</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (BillItem item : billItems) {
                            double total = item.getQuantity() * item.getPricePerUnit();
                            grandTotal += total;
                        %>
                        <tr>
                            <td><%= item.getMedicineName() %></td>
                            <td>₹<%= item.getPricePerUnit() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td>₹<%= total %></td>
                        </tr>
                        <% 
                            }
                        %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" class="total-label">Grand Total:</td>
                            <td class="total-amount">₹<%= grandTotal %></td>
                        </tr>
                    </tfoot>
                </table>
            <% } else { %>
                <p class="no-items">No items found for this bill.</p>
            <% } %>
        </section>

        <section class="actions">
            <button onclick="window.print()" class="btn-primary">Print Bill</button>
            <a href="ViewBillsServlet" class="btn-secondary">Back to Bills</a>
        </section>
    </div>
</body>
</html>
