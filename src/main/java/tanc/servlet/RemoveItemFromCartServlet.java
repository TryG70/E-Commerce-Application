package tanc.servlet;

import tanc.model.Cart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "remove-item-from-cart", value = "/remove-item-from-cart")
public class RemoveItemFromCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter writer = response.getWriter()) {

            String id = request.getParameter("id");

            if (id != null) {

                ArrayList<Cart> carts = (ArrayList<Cart>) request.getSession().getAttribute("c-list");
                if (carts != null) {
                    for (Cart c: carts) {
                        if(c.getId() == Integer.parseInt(id)) {
                            carts.remove(carts.indexOf(c));
                            break;
                        }
                    }
                }
                response.sendRedirect("cart.jsp");


            } else {
                response.sendRedirect("cart.jsp");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
