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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taibd.common.Constants;
import taibd.common.MyErrors;
import taibd.model.User;
import taibd.model.UserDAO;

/**
 *
 * @author HOME
 */
@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/register"})
public class RegisterUserServlet extends HttpServlet {

    private static final String REGISTER_USER_PAGE = "pages/register.jsp";
    private static final String LOGIN_USER_PAGE = "pages/login.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             RequestDispatcher rd = request.getRequestDispatcher(REGISTER_USER_PAGE);
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
                User found = userDAO.findByUsername(username);
                if(found != null){
                    errors.addErrors("FOUND", "Username existed, please try another username");
                } else {
                    User newUser = new User();
                    newUser.setRole(Constants.USER_ROLE);
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    boolean res = userDAO.insertUser(newUser);
                    if(!res) errors.addErrors("ERRORS", "Can not register user account right now");
                }
            }
            
            request.setAttribute("errors", errors);
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            
            String url = REGISTER_USER_PAGE;
            if(errors.isEmpty()){
                url = LOGIN_USER_PAGE;
                request.setAttribute("registerSuccess", true);
            }
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
