<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Medicine" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Medicine</title>
    <link rel="stylesheet" href="updateMedicines.css"> <!-- NEW: Link to the new CSS file -->
    <link rel="stylesheet" href="dashboard_styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
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
                    <li><a href="generateInventoryReport.jsp"><i class="fas fa-box"></i>Inventory</a></li>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>Logout</a></li>
                </ul>
            </nav>
        </aside>
        <main class="content">
            <header class="page-header">
                <h1>Update Medicine</h1>
                <a href="manageMedicines.jsp" class="back-link">Back to Manage Medicines</a>
            </header>

            <section class="update-medicine">
                <%
                    Medicine medicine = (Medicine) request.getAttribute("medicine");
                    if (medicine != null) {
                %>
                <form action="MedicineServlet" method="post" class="medicine-form">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= medicine.getMedicineId() %>">
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" value="<%= medicine.getName() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" required><%= medicine.getDescription() %></textarea>
                    </div>
                    <div class="form-group">
                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" step="0.01" min="0" value="<%= medicine.getPrice() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="stock">Stock:</label>
                        <input type="number" id="stock" name="stock" min="0" value="<%= medicine.getStock() %>" required>
                    </div>
                    <button type="submit" class="btn-primary">Update Medicine</button>
                </form>
                <% } else { %>
                <p class="error-message">Medicine not found.</p>
                <% } %>
            </section>
        </main>
    </div>
</body>
</html>
