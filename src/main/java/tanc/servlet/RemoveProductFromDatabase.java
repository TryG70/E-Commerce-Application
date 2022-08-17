package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.ProductDao;
import tanc.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "RemoveProductFromDatabase", value = "/RemoveProductFromDatabase")
public class RemoveProductFromDatabase extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");


        try (PrintWriter writer = response.getWriter()){
            ProductDao productDao = new ProductDao(DatabaseConnection.getConnection());
            boolean removed = productDao.deleteProductToDb(name, category);

            if(removed) {
                response.sendRedirect("removeProduct.jsp");
            } else {
                writer.println("Product removal failed!");
                response.sendRedirect("removeProduct.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
