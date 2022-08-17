package tanc.servlet;

import tanc.connection.DatabaseConnection;
import tanc.dao.UserDao;
import tanc.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            String email = request.getParameter("login-email");
            String password = request.getParameter("login-password");

            if (email.equalsIgnoreCase("trygodnwakwasi@gmail.com")) {
                response.sendRedirect("admin.jsp");
            } else {
                try {
                    UserDao userDao = new UserDao(DatabaseConnection.getConnection());
                    User user = userDao.userLogin(email, password);

                    if(user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("id" , user.getId());
                        request.getSession().setAttribute("TryGod", user );
//                    request.getSession().setAttribute("id", user.getId() );
                        response.sendRedirect("index.jsp");
                    } else {
                        writer.print("Login failed");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
