# Web Medical Management System

A Java-based web application designed to simplify pharmacy operations by managing medicines, inventory, billing, and sales activities through a centralized system.

 1. Project Overview

The Web Medical Management System is an academic project developed to computerize and streamline the daily operations of a pharmacy.

The application provides a centralized platform for managing medicine information, monitoring stock levels, processing customer bills, and generating sales reports.

It supports role-based access for administrators and pharmacists, allowing users to perform operations according to their responsibilities.

The system aims to reduce manual work, improve inventory accuracy, simplify billing operations, and provide useful insights into pharmacy sales.

 2. Problem Statement

Traditional pharmacy management often involves maintaining medicine records, tracking inventory, calculating bills, and monitoring sales manually.

These processes can result in:
- Difficulty maintaining accurate medicine records.
- Errors in stock calculations.
- Time-consuming billing operations.
- Challenges in monitoring medicine availability.
- Difficulty analyzing sales performance.

The Web Medical Management System addresses these challenges by providing an integrated web-based solution for pharmacy management.

 3. Objectives

- Develop a centralized pharmacy management application.
- Simplify medicine and inventory management.
- Implement secure, role-based access.
- Automate billing and sales calculations.
- Maintain accurate stock information after sales.
- Generate inventory and sales reports.
- Provide graphical representations of sales data.

 4. Technology Stack

| Technology    | Purpose                                  |
|---------------|------------------------------------------|
| Java          | Backend programming                      |
| Java Servlets | Request processing and application logic |
| JSP           | Dynamic web pages                        |
| JDBC          | Database connectivity                    |
| MySQL         | Relational database                      |
| HTML5         | Web page structure                       |
| CSS3          | User interface styling                   |
| Apache Tomcat | Web application server                   |
| Eclipse IDE   | Development environment                  |

 5. User Roles

# Administrator

The administrator manages the overall pharmacy operations.

Responsibilities include:
- Managing medicine records.
- Adding, updating, and deleting medicines.
- Managing medicine prices and stock quantities.
- Monitoring inventory.
- Viewing sales reports.
- Accessing sales analytics.

# Pharmacist

The pharmacist handles day-to-day pharmacy operations.

Responsibilities include:
- Viewing and searching medicines.
- Checking medicine availability.
- Handling medicine sales.
- Generating customer bills.
- Monitoring stock availability.

 6. Main Modules and Features

# 6.1 User Authentication and Authorization
- User login functionality.
- Role-based access for administrators and pharmacists.
- Session management.
- Restricted access to authorized operations.

# 6.2 Medicine Management
- Add new medicine records.
- Update existing medicine information.
- Delete medicine records.
- Maintain medicine names, descriptions, prices, and stock quantities.
- Search and retrieve medicine information.

# 6.3 Inventory Management
- Monitor available medicine stock.
- Update stock quantities.
- Track stock changes after sales.
- Generate inventory reports.
- Identify low-stock medicines.
- View all inventory items.

# 6.4 Billing Management
- Process medicine sales.
- Select medicines and quantities.
- Calculate item totals and bill amounts.
- Generate customer bills.
- Update medicine stock after successful sales transactions.

# 6.5 Sales Management
- Maintain medicine sales records.
- Store sales transaction details.
- Retrieve sales information.
- Monitor sales activities.
- Generate sales reports.

# 6.6 Sales Analytics and Reports
- Generate monthly sales reports.
- Analyze medicine-wise sales.
- Identify top-selling medicines.
- Display sales trends.
- Analyze pharmacist contributions.
- Generate graphical reports using charts.

 7. Application Architecture

The application follows a layered Java web application architecture.

Presentation Layer
- JSP
- HTML
- CSS

Controller Layer
- Java Servlets
- Request handling
- Session management

Business and Data Access Layer
- Java classes
- DAO implementations
- Business logic

Database Layer
- MySQL
- JDBC connectivity

The application follows the Model-View-Controller (MVC) architectural pattern to separate presentation, request processing, and data management responsibilities.

 8. Database Management

The application uses MySQL as its relational database.

JDBC is used to establish database connections and execute SQL operations.

The database maintains information related to:
- User accounts and roles.
- Medicine records.
- Inventory and stock information.
- Sales transactions.

The database is designed to maintain relationships between medicines, users, and sales records while supporting efficient data retrieval and management.

 9. Project Structure

The project is developed as an Eclipse Dynamic Web Project.

The main source packages are organized as follows:

<img width="820" height="486" alt="image" src="https://github.com/user-attachments/assets/31218c52-e6df-4408-b84d-ae0425bdd8de" />


*Note: The directory layout may vary depending on the Eclipse project configuration.*

 10. Installation and Setup

# Prerequisites

Install the following software:

- JDK 8 or compatible Java version for the original project configuration.
- Eclipse IDE for Enterprise Java and Web Developers.
- Apache Tomcat server.
- MySQL Server.
- MySQL Workbench.

# Step 1: Clone the Repository

git clone https://github.com/Shabarish2311/WebMedicalManagement.git

# Step 2: Import into Eclipse

1. Open Eclipse IDE.
2. Select File → Import.
3. Choose Existing Projects into Workspace.
4. Select the cloned project directory.
5. Click Finish.

# Step 3: Configure MySQL

1. Start MySQL Server.
2. Create the project database.
3. Import the provided SQL database script, if included.
4. Configure the database connection details in the DBUtility package.

Update the following details according to your MySQL configuration:

- Database URL
- Database name
- Username
- Password

# Step 4: Configure Apache Tomcat

1. Add Apache Tomcat to Eclipse.
2. Configure the project runtime.
3. Add the project to the Tomcat server.
4. Start the server.

# Step 5: Run the Application

Right-click the project and select:

Run As → Run on Server

Open the application in your browser using the deployed Tomcat context path.

 11. Key Technical Concepts Implemented

- Java Object-Oriented Programming.
- Model-View-Controller (MVC).
- Java Servlets and JSP.
- JDBC database connectivity.
- CRUD operations.
- Relational database management.
- Session management.
- Role-based access control.
- Exception handling.
- SQL queries and joins.
- Data visualization and sales analytics.

 12. Future Enhancements

- Medicine expiry-date tracking.
- Barcode-based medicine identification.
- Supplier and purchase management.
- Automated low-stock notifications.
- PDF invoice generation.
- Advanced sales forecasting.
- Improved dashboard and analytics.
- Enhanced security and audit logging.

 13. Academic Project Information

Project Name: Web Medical Management System

Project Type: Academic / Bachelor's Capstone Project

Primary Technologies: Java, JSP, Servlets, JDBC, MySQL, HTML and CSS.

Domain: Pharmacy and Healthcare Management

-------------------------------------------------------------------------------------------------------------------------------------------------------
Web Medical Management System

A web-based solution for managing pharmacy operations, medicine inventory, billing, and sales analytics.
