<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Medicine" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Inventory Report - Web Medical Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="dashboard_styles.css">
    <link rel="stylesheet" href="inventory_report_styles.css"> <!-- NEW: Link to the new CSS file -->
</head>
<body>
    <div class="dashboard">
        <aside class="sidebar">
            <div class="logo">
                <i class="fas fa-clinic-medical"></i>
                <span>Aster Pharmacy</span>
            </div>
            <nav>
                <ul>
                   <li><a href="dashboard.jsp"><i class="fas fa-home"></i>Dashboard</a></li>
                   <li><a href="manageMedicines.jsp"><i class="fas fa-pills"></i>Manage Medicines</a></li>
                   <li><a href="managePharmacists.jsp"><i class="fas fa-users"></i> Manage Pharmacists</a></li>
                   <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>Logout</a></li>
                </ul>
            </nav>
        </aside>
        <main class="content">
            <header class="page-header">
                <h1>Inventory Report</h1>
            </header>
            <section class="inventory-report">
                <form action="GenerateInventoryReportServlet" method="post" class="report-form">
                    <div class="form-group">
                        <label for="reportType">Report Type:</label>
                        <select id="reportType" name="reportType">
                            <option value="all">All Items</option>
                            <option value="lowStock">Low Stock Items</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-primary">Generate Report</button>
                </form>

                <% if (request.getAttribute("inventoryItems") != null) { %>
                    <div class="inventory-table">
                        <table class="report-table">
                            <thead>
                                <tr>
                                    <th>Medicine Name</th>
                                    <th>Description</th>
                                    <th>Current Stock</th>
                                    <th>Reorder Level</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                List<Medicine> inventoryItems = (List<Medicine>) request.getAttribute("inventoryItems");
                                for (Medicine item : inventoryItems) {
                                    int stock = item.getStock();
                                    String reorderLevel = (stock < 20) ? "Reorder level 50" : "0";
                                %>
                                    <tr>
                                        <td><%= item.getName() %></td>
                                        <td><%= item.getDescription() %></td>
                                        <td><%= stock %></td>
                                        <td><%= reorderLevel %></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>

            </section>
        </main>
    </div>
</body>
</html>

