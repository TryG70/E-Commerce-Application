package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RemoveFromWishList", value = "/RemoveFromWishList")
public class RemoveFromWishlistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int UserId = Integer.parseInt(request.getParameter("user_id"));
        int productId = Integer.parseInt(request.getParameter("product_id"));

        System.out.println(UserId);
        System.out.println(productId);
        try {
            UserDao userDao = new UserDao(DatabaseConnection.getConnection());
            if(userDao.removeWishList(UserId , productId)){
                response.sendRedirect("wishList.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
