package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.OrderDao;
import tanc.dao.UserDao;
import tanc.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "sign-up", value = "/sign-up")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String re_pass = request.getParameter("re_pass");


        RequestDispatcher dispatcher = null;
        Connection connection = null;
        if(name == null || name.equals(" ")){
            request.setAttribute("status", "invalidUsername");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(email == null || email.equals(" ")){
            request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(password == null || password.equals(" ")){
            request.setAttribute("status", "invalidPassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        else if(!password.equals(re_pass)){
            request.setAttribute("status", "invalidRePassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }

        try{

            UserDao userDao = new UserDao(DatabaseConnection.getConnection());
            int count = userDao.populateUserTable(name, email, password);
            dispatcher = request.getRequestDispatcher("registration.jsp");
            if(count > 0){
                request.setAttribute("status", "success");
            }else{
                request.setAttribute("status","failed");
            }
            dispatcher.forward(request,response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
