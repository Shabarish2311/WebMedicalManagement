<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
<%@ page import="DBUtility.DBConnection" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill Generated - Aster Pharmacy</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Bill Generated Successfully</h1>
        <%
            String billId = request.getParameter("billId");
            String pdfPath = "";
            
            if (billId != null) {
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                
                try {
                    conn = DBUtility.DBConnection.getConnection();
                    String query = "SELECT pdf_path FROM bills WHERE bill_id = ?";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(billId));
                    rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        pdfPath = rs.getString("pdf_path");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        %>
        
        <% if (!pdfPath.isEmpty()) { %>
            <p>Your bill has been generated successfully. Click the link below to download:</p>
            <a href="<%= request.getContextPath() %>/DownloadServlet?filePath=<%= pdfPath %>" class="btn">Download Bill</a>
        <% } else { %>
            <p>Failed to retrieve the bill. Please try again.</p>
        <% } %>
        
        <a href="dashboard.jsp" class="btn">Back to Dashboard</a>
    </div>
</body>
</html>
