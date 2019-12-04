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
import taibd.model.Category;
import taibd.model.CategoryDAO;
import taibd.model.Product;
import taibd.model.ProductDAO;

/**
 *
 * @author HOME
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

   private static final String PRODUCT_DETAILS_PAGE = "pages/product.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String productId = request.getParameter("productId");
        System.out.println("productId: " + productId);
        try {
            Integer id = Integer.parseInt(productId);
            ProductDAO productDAO = new ProductDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            
            Product product = productDAO.findById(id);
            Category category = null;
            if(product != null){
                category = categoryDAO.findById(product.getCategoryId());
            }
            request.setAttribute("product", product);
            request.setAttribute("category", category);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            RequestDispatcher rd = request.getRequestDispatcher(PRODUCT_DETAILS_PAGE);
            rd.forward(request, response);
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
