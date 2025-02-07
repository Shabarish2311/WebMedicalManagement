<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Medicine"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Update Stock - Web Medical Management System</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="dashboard_styles.css">
<link rel="stylesheet" href="update_stock_styles.css">
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
					<li><a href="generateInventoryReport.jsp"><i class="fas fa-box"></i>
							Inventory</a></li>
					<li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i>
							Logout</a></li>
				</ul>
			</nav>
		</aside>
		<main class="content">
		
            <header class="page-header">
                <h1>Update Stock</h1>
                <a href="dashboard.jsp" class="back-link">Back to DashBoard</a>
            </header>
            <section class="update-stock-form">
                <form action="UpdateStockServlet" method="post" class="stock-form">
                    <div class="form-group">
                        <label for="medicineId">Select Medicine:</label>
                        <select id="medicineId" name="medicineId" required>
                            <option value="">-- Select Medicine --</option>
                            <%
                            List<Medicine> medicines = (List<Medicine>) request.getAttribute("medicines");
                            if (medicines != null && !medicines.isEmpty()) {
                                for (Medicine medicine : medicines) {
                            %>
                                <option value="<%=medicine.getMedicineId()%>"><%=medicine.getName()%> - <%=medicine.getDescription()%></option>
                            <%
                                }
                            } else {
                            %>
                                <option value="">No medicines available</option>
                            <%
                            }
                            %>
                        </select>
                    </div>
					<div class="form-group">
						<label for="quantity">Quantity to Add:</label> <input
							type="number" id="quantity" name="quantity" min="1" required>
					</div>
					<button type="submit" class="btn-primary">Update Stock</button>
				</form>
			</section>
		</main>
	</div>
</body>
</html>
