<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Sale"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Sales Report - Web Medical Management System</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="dashboard_styles.css">
<link rel="stylesheet" href="sales_report_styles.css">
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
				   <li><a href="managePharmacists.jsp"><i class="fas fa-users"></i> Manage Pharmacists</a></li>
				   <li><a href="generateInventoryReport.jsp"><i class="fas fa-clipboard-list"></i> Generate Inventory Report</a></li>
				   <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>Logout</a></li>
				</ul>
			</nav>
		</aside>
		<main class="content">
			<header>
				<h1>Sales Report</h1>
			</header>
			<section class="sales-report">
				<div class="chart-container">
					<h2>Monthly Sales</h2>
					<img src="SalesReportServlet?type=monthly"
						alt="Monthly Sales Chart">
				</div>
				<div class="chart-container">
					<h2>Top Selling Medicines</h2>
					<img src="SalesReportServlet?type=topProducts"
						alt="Top Selling Medicines Chart">
				</div>
				<div class="chart-container">
					<h2>Sales by Day</h2>
					<img src="SalesReportServlet?type=salesByDay"
						alt="Sales by Day Chart">
				</div>
				<div class="chart-container">
					<h2>Medicine Sales Distribution</h2>
					<img src="SalesReportServlet?type=medicineSalesPie"
						alt="Medicine Sales Distribution Chart">
				</div>
				
			</section>
		</main>
	</div>
</body>
</html>
