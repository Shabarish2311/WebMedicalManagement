package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MedicineDAO;
import daoImpl.MedicineDAOImpl;
import model.Medicine;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {

    private MedicineDAO medicineDAO;

    @Override
    public void init() {
        medicineDAO = new MedicineDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Medicine medicine = new Medicine(name, description, price, stock);
            medicineDAO.addMedicine(medicine);
            response.sendRedirect("manageMedicines.jsp");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            medicineDAO.deleteMedicine(id);
            response.sendRedirect("manageMedicines.jsp");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Medicine medicine = new Medicine(id, name, description, price, stock);
            medicineDAO.updateMedicine(medicine);
            response.sendRedirect("manageMedicines.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updatePage".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Medicine medicine = medicineDAO.getMedicineById(id);
            
            if (medicine != null) {
                request.setAttribute("medicine", medicine); // Set the medicine details to the request
                request.getRequestDispatcher("updateMedicne.jsp").forward(request, response); // Forward to the JSP
            } else {
                response.sendRedirect("manageMedicines.jsp"); // If not found, redirect back
            }
        }
    }
}
