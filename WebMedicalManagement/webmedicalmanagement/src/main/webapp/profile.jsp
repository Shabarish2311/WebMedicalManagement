<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Profile - Web Medical Management System</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="dashboard_styles.css">
<link rel="stylesheet" href="profile_styles.css">

</head>
<body>
	<div class="dashboard">
		<aside class="sidebar">
			<div class="logo">
				<i class="fas fa-clinic-medical"></i> <span>Aster Pharmacy</span>
			</div>
			<nav>
				<ul>
					<li><a href="dashboard.jsp" class="active"><i class="fas fa-home"></i>
							DashBoard</a></li>
					<li><a href="ViewBillsServlet"><i
							class="fas fa-file-invoice-dollar"></i> View Bills</a></li>
					<li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>
							Logout</a></li>
				</ul>
			</nav>
		</aside>
		<main class="content">
			<header>
				<h1>User Profile</h1>
			</header>
			<section class="profile-content">
				<div class="profile-info">
					<h2>
						<i class="fas fa-user-circle"></i>
						<%= session.getAttribute("username") %></h2>
					<p>
						<strong>Role:</strong>
						<%= session.getAttribute("role") %></p>
				</div>
				<div class="change-password">
					<h3>Change Password</h3>
					<form action="ChangePasswordServlet" method="post">
						<div class="form-group">
							<label for="currentPassword">Current Password:</label> <input
								type="password" id="currentPassword" name="currentPassword"
								required>
						</div>
						<div class="form-group">
							<label for="newPassword">New Password:</label> <input
								type="password" id="newPassword" name="newPassword" required>
						</div>
						<div class="form-group">
							<label for="confirmPassword">Confirm New Password:</label> <input
								type="password" id="confirmPassword" name="confirmPassword"
								required>
						</div>
						<button type="submit" class="btn-primary">Change Password</button>
					</form>
				</div>
			</section>
		</main>
	</div>
	<% String success = request.getParameter("success"); %>
	<% String error = request.getParameter("error"); %>
	<% if (success != null) { %>
	<p class="success-message"><%= success %></p>
	<% } %>
	<% if (error != null) { %>
	<p class="error-message"><%= error %></p>
	<% } %>

</body>
</html>

