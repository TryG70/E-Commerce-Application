package tanc.servlet;

import tanc.model.Cart;


import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name = "add-to cart", value = "/add-to cart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("TryGod") == null) {
            response.sendRedirect("login.jsp");
        } else {

            ArrayList<Cart> cartArrayList = new ArrayList<>();

            int id = Integer.parseInt(request.getParameter("id"));
            Cart cart = new Cart();
            cart.setId(id);
            cart.setQuantity(1);


            HttpSession session = request.getSession();
            ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("c-list");


            if(cartList== null) {
                cartArrayList.add(cart);
                session.setAttribute("c-list", cartArrayList);
                response.sendRedirect("index.jsp");
            } else {
                cartArrayList = cartList;
                response.sendRedirect("cart.jsp");
                boolean exist = false;

                for (Cart c: cartList) {
                    if(c.getId() == id) {
                        exist = true;
                    }
                }
                if(!exist) {
                    cartArrayList.add(cart);
                }
            }
        }



    }

}
