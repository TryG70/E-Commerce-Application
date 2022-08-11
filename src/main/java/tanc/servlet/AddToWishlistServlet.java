package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.UserDao;
import tanc.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddToWishlistServlet", value = "/AddToWishlistServlet")
public class AddToWishlistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        int productId = Integer.parseInt(request.getParameter("productId"));



        UserDao userDao = null;
        boolean isAdded = false;
        try {
            userDao = new UserDao(DatabaseConnection.getConnection());
            isAdded = userDao.populateWishListTable(userId, productId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (isAdded) {
            response.sendRedirect("index.jsp");
        }

    }
}
