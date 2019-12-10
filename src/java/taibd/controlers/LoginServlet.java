/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import taibd.common.MyErrors;
import taibd.model.User;
import taibd.model.UserDAO;

/**
 *
 * @author HOME
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "pages/login.jsp";
    private static final String HOME_SERVLET = "/home";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(LOGIN_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            MyErrors errors = new MyErrors();
             
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            
            if(username == null || username.trim().equals("")){
                errors.addErrors("username", "Username is required!");
            }
            if(password == null || password.trim().equals("")){
                errors.addErrors("password", "Password is required!");
            }
            
            if(errors.isEmpty()){
                UserDAO userDAO = new UserDAO();
                User user = userDAO.checkLogin(username, password);
                if(user == null) errors.addErrors("NOT_FOUND", "Username and password do not match!");
                else {
                    HttpSession session = request.getSession();
                    user.setPassword(null);
                    session.setAttribute("user", user);
                    session.setMaxInactiveInterval(60*60*24);
//                    Cookie ck = new Cookie("username",user.getUsername());  
//                    ck.setMaxAge(0);  
//                    response.addCookie(ck);  
                }
            }
            
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            
            String url = LOGIN_PAGE;
            if(errors.isEmpty()) url = HOME_SERVLET;
            
            if(url.equals(LOGIN_PAGE)){
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                String contextPath = this.getServletContext().getContextPath();
                response.sendRedirect(contextPath + url);
            }
            
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
