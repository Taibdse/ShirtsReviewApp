/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taibd.entity.ProductXMLWrapper;
import taibd.model.Category;
import taibd.model.CategoryDAO;
import taibd.model.Product;
import taibd.model.ProductDAO;
import taibd.model.VotesDAO;
import taibd.utilities.ObjectUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

   private static final String PRODUCT_DETAILS_PAGE = "pages/product.jsp";
   private static final String PRODUCTS_PAGE = "pages/products.jsp";
   
   private static final ProductDAO productDAO = new ProductDAO();
   private static final CategoryDAO categoryDAO = new CategoryDAO();
   private static final VotesDAO votesDAO = new VotesDAO();
   
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
        
        String url = "";
        if(productId == null){
            url = handleProductsPage(request, response);
        } else {
            Integer id = Integer.parseInt(productId);
            url = handleProductDetailsPage(id, request, response);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
    
    public String handleProductDetailsPage(int productId, HttpServletRequest request, HttpServletResponse response){
        ProductXMLWrapper product = null;
        Category category = null;
        try {
            Product p = productDAO.findById(productId);
            if(p != null){
                category = categoryDAO.findById(p.getCategoryId());
                int views = p.getViews() != null ? (p.getViews() + 1) : 1;
                p.setViews(views);
                productDAO.updateProduct(p);
                double avgVotes = votesDAO.findProductAVGVotes(productId);
                product = ObjectUtils.mapProductDTOToProductWrapper(p);
                product.setAvgVotes(avgVotes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            request.setAttribute("product", product);
            request.setAttribute("category", category);
        }
        return PRODUCT_DETAILS_PAGE;
    }
    
    public String handleProductsPage(HttpServletRequest request, HttpServletResponse response){
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("categories", categories);
        return PRODUCTS_PAGE;
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
