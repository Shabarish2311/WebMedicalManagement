package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/SalesReportServlet")
public class SalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");

		if ("monthly".equals(type)) {
			generateMonthlySalesChart(response);
		} else if ("topProducts".equals(type)) {
			generateTopSellingMedicinesChart(response);
		} else if ("salesByDay".equals(type)) {
			generateSalesByDayChart(response);
		} else if ("medicineSalesPie".equals(type)) {
			generateMedicineSalesPieChart(response);
		} 
	}

	// Monthly Sales (Bar Chart)
	private void generateMonthlySalesChart(HttpServletResponse response) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		try (Connection con = DBUtility.DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT MONTH(sale_date) AS month, SUM(total_price) AS total FROM sales GROUP BY MONTH(sale_date)");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int month = rs.getInt("month");
				String monthName = getMonthName(month);
				dataset.addValue(rs.getDouble("total"), "Sales", monthName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"Monthly Sales", "Month", "Total Sales",
				dataset, PlotOrientation.VERTICAL, false, true, false);

		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		ChartUtils.writeChartAsPNG(out, chart, 800, 600);
		out.close();
	}

	// Top Selling Medicines (Bar Chart)
	private void generateTopSellingMedicinesChart(HttpServletResponse response) throws IOException {
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    try (Connection con = DBUtility.DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(
	             "SELECT m.name, SUM(s.quantity) AS total_sold " +
	             "FROM sales s " +
	             "JOIN medicines m ON s.medicine_id = m.medicine_id " +
	             "GROUP BY m.name " +
	             "ORDER BY total_sold DESC " +
	             "LIMIT 5"
	         );
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            dataset.addValue(rs.getDouble("total_sold"), "Sales", rs.getString("name"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createBarChart(
	        "Top Selling Medicines", "Medicine Name", "Total Sold",
	        dataset, PlotOrientation.VERTICAL, false, true, false);

	    response.setContentType("image/png");
	    OutputStream out = response.getOutputStream();
	    ChartUtils.writeChartAsPNG(out, chart, 800, 600);
	    out.close();
	}


	// Sales by Day (Bar Chart)
	private void generateSalesByDayChart(HttpServletResponse response) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		try (Connection con = DBUtility.DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT DAYOFWEEK(sale_date) AS day, SUM(total_price) AS total FROM sales GROUP BY DAYOFWEEK(sale_date)");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int dayOfWeek = rs.getInt("day");
				String dayName = getDayName(dayOfWeek);
				dataset.addValue(rs.getDouble("total"), "Sales", dayName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFreeChart chart = ChartFactory.createBarChart(
				"Sales by Day", "Day", "Total Sales",
				dataset, PlotOrientation.VERTICAL, false, true, false);

		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		ChartUtils.writeChartAsPNG(out, chart, 800, 600);
		out.close();
	}

	// Medicine Sales Distribution (Pie Chart)
	private void generateMedicineSalesPieChart(HttpServletResponse response) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try (Connection con = DBUtility.DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"SELECT m.name, SUM(s.total_price) AS total_sales " +
						"FROM sales s JOIN medicines m ON s.medicine_id = m.medicine_id " +
						"GROUP BY m.name ORDER BY total_sales DESC");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				dataset.setValue(rs.getString("name"), rs.getDouble("total_sales"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFreeChart chart = ChartFactory.createPieChart(
				"Medicine Sales Distribution", dataset, true, true, false);

		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		ChartUtils.writeChartAsPNG(out, chart, 800, 600);
		out.close();
	}

	// Helper methods for converting integers to day and month names
	private String getMonthName(int month) {
		String[] monthNames = {"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"};
		return monthNames[month - 1];
	}

	private String getDayName(int dayOfWeek) {
		String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		return dayNames[dayOfWeek - 1];
	}
}
