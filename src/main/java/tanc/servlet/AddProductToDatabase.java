package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.ProductDao;
import tanc.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddProductToDatabase", value = "/AddProductToDatabase")
public class AddProductToDatabase extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        String image = request.getParameter("image");

        Product product = new Product(name, category, price, image);
//        HttpSession session = request.getSession();
//        session.setAttribute("TryGod", product);

        try {
            ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());
             boolean added = productDao.addProductToDb(product);

             if(added) {
                 response.sendRedirect("addProduct.jsp");
             }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
