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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "order-now", value = "/order-now")
public class OrderNowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter()){

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();

            User TryGod = (User) request.getSession().getAttribute("TryGod");
            if(TryGod != null) {

                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));

                if (productQuantity <= 0) {
                    productQuantity = 1;
                }

                Order order = new Order();
                order.setId(Integer.parseInt(productId));
                order.setUserId(TryGod.getId());
                order.setQuantity(productQuantity);
                order.setDate(dateFormat.format(date));

                OrderDao orderDao = new OrderDao(DatabaseConnection.getConnection());
                boolean result = orderDao.populateOrderTable(order);

                if(result) {
                    ArrayList<Cart> carts = (ArrayList<Cart>) request.getSession().getAttribute("c-list");
                    if (carts != null) {
                        for (Cart c: carts) {
                            if(c.getId() == Integer.parseInt(productId)) {
                                carts.remove(carts.indexOf(c));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("orders.jsp");
                } else {
                    writer.print("order failed");
                }



            } else {
                response.sendRedirect("login.jsp ");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
