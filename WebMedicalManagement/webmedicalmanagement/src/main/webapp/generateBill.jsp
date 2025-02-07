<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.BillItem"%>
<%@ page import="model.Medicine"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Bill - Aster Pharmacy</title>
    <link rel="stylesheet" href="generateBill.css"> <!-- NEW: Link to the new CSS file -->
    <link rel="stylesheet" href="dashboard_styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <script>
        function increaseQuantity() {
            var quantityInput = document.getElementById("quantity");
            var currentQuantity = parseInt(quantityInput.value);
            quantityInput.value = currentQuantity + 1;
        }

        function decreaseQuantity() {
            var quantityInput = document.getElementById("quantity");
            var currentQuantity = parseInt(quantityInput.value);
            if (currentQuantity > 1) {
                quantityInput.value = currentQuantity - 1;
            }
        }
    </script>
</head>
<body>
    <div class="dashboard">
        <aside class="sidebar">
            <div class="logo">
                <i class="fas fa-clinic-medical"></i> <span>Aster Pharmacy</span>
            </div>
            <nav>
                <ul>
                    <li><a href="dashboard.jsp"><i class="fas fa-home"></i>Dashboard</a></li>
                    <li><a href="manageMedicines.jsp"><i class="fas fa-pills"></i>Manage Medicines</a></li>
                    <li><a href="generateInventoryReport.jsp"><i class="fas fa-box"></i> Inventory</a></li>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </nav>
        </aside>

        <div class="content">
            <header class="page-header">
                <h1>Generate Bill</h1>
                <a href="dashboard.jsp" class="back-link">Back to Dashboard</a>
            </header>

            <section class="add-item">
                <form action="GenerateBillServlet" method="post" class="add-item-form">
                    <input type="hidden" name="action" value="addItem">
                    <div class="form-group">
                        <label for="medicineId">Select Medicine:</label>
                        <select name="medicineId" required>
                            <% 
                            List<Medicine> medicines = (List<Medicine>) request.getAttribute("medicines");
                            if (medicines != null) {
                                for (Medicine med : medicines) { 
                            %>
                            <option value="<%= med.getMedicineId() %>"><%= med.getName() %> - ₹<%= med.getPrice() %></option>
                            <% 
                                }
                            }
                            %>
                        </select>
                    </div>

                    <div class="form-group quantity-group">
                        <label for="quantity">Quantity:</label>
                        <div class="quantity-controls">
                            <button type="button" onclick="decreaseQuantity()" class="quantity-button">-</button>
                            <input type="number" id="quantity" name="quantity" required min="1" value="1">
                            <button type="button" onclick="increaseQuantity()" class="quantity-button">+</button>
                        </div>
                    </div>

                    <button type="submit" class="btn-primary">Add Item</button>
                </form>
            </section>

            <section class="bill-items">
                <h2>Bill Items</h2>
                <table class="bill-table">
                    <thead>
                        <tr>
                            <th>Medicine Name</th>
                            <th>Quantity</th>
                            <th>Price Per Unit</th>
                            <th>Total Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
                        double totalBillPrice = 0.0; // Variable to store total bill price

                        if (billItems != null && !billItems.isEmpty()) {
                            for (BillItem item : billItems) {
                                double itemTotal = item.getQuantity() * item.getPricePerUnit();
                                totalBillPrice += itemTotal; // Accumulate total price
                        %>
                        <tr>
                            <td><%= item.getMedicineName() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td>₹<%= item.getPricePerUnit() %></td>
                            <td>₹<%= itemTotal %></td>
                        </tr>
                        <% 
                            }
                        } else { 
                        %>
                        <tr>
                            <td colspan="4">No items added yet.</td>
                        </tr>
                        <% } %>
                    </tbody>
                    <% if (billItems != null && !billItems.isEmpty()) { %>
                    <tfoot>
                        <tr>
                            <td colspan="3" class="total-label">Total Bill:</td>
                            <td class="total-price">₹<%= totalBillPrice %></td>
                        </tr>
                    </tfoot>
                    <% } %>
                </table>
            </section>

            <section class="actions">
                <form action="GenerateBillServlet" method="post" class="generate-bill-form">
                    <input type="hidden" name="action" value="generateBill">
                    <button type="submit" class="btn-generate">Generate Bill</button>
                </form>
            </section>
        </div>
    </div>
</body>
</html>
