<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Medicine" %>
<%@ page import="daoImpl.MedicineDAOImpl" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Medicines</title>
    <link rel="stylesheet" href="manageMedicines.css">
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
				   <li><a href="dashboard.jsp"><i class="fas fa-home"></i> Dashboard</a></li>
				   <li><a href="managePharmacists.jsp"><i class="fas fa-users"></i> Manage Pharmacists</a></li>
				   <li><a href="generateInventoryReport.jsp"><i class="fas fa-clipboard-list"></i> Generate Inventory Report</a></li>
				   <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
				</ul>
			</nav>
		</aside>
    <div class="container">
        <header>
            <h1>Manage Medicines</h1>
            <a href="dashboard.jsp" class="back-link">Back to DashBoard</a>
        </header>

        <main>
            <section class="add-medicine">
                <h2>Add New Medicine</h2>
                <form action="MedicineServlet" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="stock">Stock:</label>
                        <input type="number" id="stock" name="stock" min="0" required>
                    </div>
                    <button type="submit" class="btn-primary">Add Medicine</button>
                </form>
            </section>

            <section class="medicine-list">
                <h2>Available Medicines</h2>
                <%
                    MedicineDAOImpl medicineDAO = new MedicineDAOImpl();
                    List<Medicine> medicines = medicineDAO.getAllMedicines();
                    if (medicines.isEmpty()) {
                %>
                <p class="no-medicines">No medicines available.</p>
                <% } else { %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Medicine medicine : medicines) { %>
                        <tr>
                            <td><%= medicine.getMedicineId() %></td>
                            <td><%= medicine.getName() %></td>
                            <td><%= medicine.getDescription() %></td>
                            <td>₹<%= String.format("%.2f", medicine.getPrice()) %></td>
                            <td><%= medicine.getStock() %></td>
                            <td class="action-buttons">
                                <form action="MedicineServlet" method="post" class="delete-form">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= medicine.getMedicineId() %>">
                                    <button type="submit" class="btn-delete">Delete</button>
                                </form>
                                <form action="MedicineServlet" method="get" class="update-form">
                                    <input type="hidden" name="action" value="updatePage">
                                    <input type="hidden" name="id" value="<%= medicine.getMedicineId() %>">
                                    <button type="submit" class="btn-update">Update</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } %>
            </section>
        </main>
    </div>
</body>
</html>