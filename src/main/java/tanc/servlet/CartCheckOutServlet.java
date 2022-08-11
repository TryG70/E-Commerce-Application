package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.OrderDao;
import tanc.model.Cart;
import tanc.model.Order;
import tanc.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "cart-check-out", value = "/cart-check-out")
public class CartCheckOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try(PrintWriter writer = response.getWriter()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();

            ArrayList<Cart> carts = (ArrayList<Cart>) request.getSession().getAttribute("c-list");
            User TryGod = (User) request.getSession().getAttribute("TryGod");

            if(carts != null && TryGod != null) {

                for (Cart c: carts) {
                    Order order = new Order();

                    order.setId(c.getId());
                    order.setUserId(TryGod.getId());
                    order.setQuantity(c.getQuantity());
                    order.setDate(dateFormat.format(date));

                    OrderDao orderDao = new OrderDao(DatabaseConnection.getConnection());
                    boolean result = orderDao.populateOrderTable(order);

                    if(!result) {
                        break;
                    }
                    carts.remove(c);
                }
                response.sendRedirect("orders.jsp");

            } else {
                if(carts == null) {
                    response.sendRedirect("cart.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
