<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Aster Pharmacy Medical Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="dashboard_styles.css">
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
                    <li><a href="#" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
                    <li><a href="profile.jsp"><i class="fas fa-user"></i> Profile</a></li>
                    <li><a href="ViewBillsServlet"><i class="fas fa-file-invoice-dollar"></i> View Bills</a></li>
                    <li><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </nav>
        </aside>
        <main class="content">
            <header>
                <h1>Welcome, <%= session.getAttribute("username") %>!</h1>
            </header>
            <section class="dashboard-content">
                <% 
                String role = (String) session.getAttribute("role");
                if ("admin".equals(role)) { 
                %>
                    <h2><i class="fas fa-user-shield"></i> Admin Dashboard</h2>
                    <div class="card-container">
                        <a href="manageMedicines.jsp" class="card">
                            <i class="fas fa-pills"></i>
                            <span>Manage Medicines</span>
                        </a>
                        <a href="managePharmacists.jsp" class="card">
                            <i class="fas fa-users"></i>
                            <span>Manage Pharmacists</span>
                        </a>
                        <a href="viewSalesReport.jsp" class="card">
                            <i class="fas fa-chart-line"></i>
                            <span>View Sales Report</span>
                        </a>
                        <a href="generateInventoryReport.jsp" class="card">
                            <i class="fas fa-clipboard-list"></i>
                            <span>Generate Inventory Report</span>
                        </a>
                    </div>
                <% } else if ("pharmacist".equals(role)) { %>
                    <h2><i class="fas fa-user-md"></i> Pharmacist Dashboard</h2>
                    <div class="card-container">
                        <a href="addMedicinePhrm.jsp" class="card">
                            <i class="fas fa-capsules"></i>
                            <span>Add Medicine</span>
                        </a>
                        <a href="UpdateStockServlet" class="card">
                            <i class="fas fa-box-open"></i>
                            <span>Update Stock</span>
                        </a>
                        <a href="GenerateBillServlet" class="card">
                            <i class="fas fa-file-invoice-dollar"></i>
                            <span>Generate Bill</span>
                        </a>
                    </div>
                <% } else { %>
                    <div class="error-container">
                        <p class="error">
                            <i class="fas fa-exclamation-triangle"></i>
                            Invalid role. Please log in again.
                        </p>
                        <a href="login.jsp" class="btn-login">Back to Login</a>
                    </div>
                <% } %>
            </section>
        </main>
    </div>
</body>
</html>