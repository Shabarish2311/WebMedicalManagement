package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.UserDAOImpl;
import model.User;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAOImpl userDAO = new UserDAOImpl();

        if ("add".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            User user = new User(0, username, password, role);
            userDAO.addUser(user);
            response.sendRedirect("managePharmacists.jsp");

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(id);
            response.sendRedirect("managePharmacists.jsp");

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = userDAO.getUserById(id);
            if (user != null) {
                user.setUsername(username);
                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }
                userDAO.updateUser(user);
            }
            response.sendRedirect("managePharmacists.jsp");
        }
    }
}
