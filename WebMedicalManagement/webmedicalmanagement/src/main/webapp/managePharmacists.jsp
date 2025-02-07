<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="daoImpl.UserDAOImpl" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Pharmacists</title>
    <link rel="stylesheet" href="adminStyles.css">
    <link rel="stylesheet" href="dashboard_styles.css">
    <link rel="stylesheet" href="managePharmacists.css"> <!-- NEW: Link to the new CSS file -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
	<<div class="dashboard">
		<aside class="sidebar">
			<div class="logo">
				<i class="fas fa-clinic-medical"></i> <span>Aster Pharmacy</span>
			</div>
			<nav>
				<ul>
				   <li><a href="dashboard.jsp"><i class="fas fa-home"></i> Dashboard</a></li>
				   <li><a href="managePharmacists.jsp"><i class="fas fa-users"></i> Manage Pharmacists</a></li>
				   <li><a href="generateInventoryReport.jsp"><i class="fas fa-clipboard-list"></i> Generate Inventory Report</a></li>
				   <li><a href="viewSalesReport.jsp"><i class="fas fa-chart-line"></i> View Sales Report</a></li>
				   <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
				</ul>
			</nav>
		</aside>
    <div class="container">
    	<header class="page-header">
    		<h1>Manage Pharmacists</h1><a href="dashboard.jsp" class="back-link">Back to Dashboard</a>

    	</header>
        <section class="add-pharmacist">
        <h2>Add New Pharmacist</h2>
        <form action="UserServlet" method="post" class="pharmacist-form">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="pharmacist">Pharmacist</option>
                </select>
            </div>
            <button type="submit" class="button">Add Pharmacist</button>
        </form>
        </section>

        <!-- List of Pharmacists -->
        <section class="pharmacist-list">
        <h2>Registered Pharmacists</h2>
        <%
            UserDAOImpl userDAO = new UserDAOImpl();
            List<User> pharmacists = userDAO.getAllUsersByRole("pharmacist");
            if (pharmacists.isEmpty()) {
        %>
            <p>No pharmacists available.</p>
        <% } else { %>
            <table class="pharmacist-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <% for (User pharmacist : pharmacists) { %>
                    <tr>
                        <td><%= pharmacist.getUserId() %></td>
                        <td><%= pharmacist.getUsername() %></td>
                        <td><%= pharmacist.getRole() %></td>
                        <td class="action-buttons">
                            <form action="UserServlet" method="post">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= pharmacist.getUserId() %>">
                                <button type="submit" class="delete-button">Delete</button>
                            </form>
                            <form action="updatePharmacist.jsp" method="get">
                                <input type="hidden" name="id" value="<%= pharmacist.getUserId() %>">
                                <button type="submit" class="update-button">Update</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } %>
        </section>


    </div>
    </div>
</body>
</html>
