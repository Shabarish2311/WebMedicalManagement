<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Bill"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Bills - Aster Pharmacy</title>
<link rel="stylesheet" href="viewBills.css"> <!-- NEW: Link to the new CSS file -->
<link rel="stylesheet" href="dashboard_styles.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
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
                    <li><a href="dashboard.jsp"><i class="fas fa-home"></i> Dashboard</a></li>
                    <li><a href="generateInventoryReport.jsp"><i class="fas fa-box"></i> Inventory</a></li>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </nav>
        </aside>
		<div class="content">  <!-- Added content div -->
			<header class="page-header">
				<a href="dashboard.jsp" class="back-link">Back to DashBoard</a>
			</header>
			<section class="bill-list">
			<h1>View Bills</h1>
			<table class="bill-table">
				<thead>
					<tr>
						<th>Bill ID</th>
						<th>Total Amount</th>
						<th>Date</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<%
                List<Bill> bills = (List<Bill>) request.getAttribute("bills");
                if (bills != null && !bills.isEmpty()) {
                    for (Bill bill : bills) {
                %>
					<tr>
						<td><%= bill.getBillId() %></td>
						<td>₹<%= bill.getTotalAmount() %></td>
						<td><%= bill.getBillDate() %></td>
						<td><a href="ShowBillServlet?billId=<%= bill.getBillId() %>"
							class="btn-primary">View Bill</a></td>
					</tr>
					<% 
                    }
                } else { %>
					<tr>
						<td colspan="4">No bills found.</td>
					</tr>
					<% } %>
				</tbody>
			</table>
			</section>
		</div> <!-- Closed content div -->
		</div>
</body>
</html>
