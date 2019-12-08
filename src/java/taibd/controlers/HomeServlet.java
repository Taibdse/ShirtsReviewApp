/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taibd.entity.ProductListXmlWrapper;
import taibd.entity.ProductXMLWrapper;
import taibd.model.Category;
import taibd.model.CategoryDAO;
import taibd.utilities.ObjectUtils;
import taibd.model.Product;
import taibd.model.ProductDAO;
import taibd.model.Vote;
import taibd.model.VotesDAO;
import taibd.utilities.JAXBUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    private final static VotesDAO votesDAO = new VotesDAO();
    
    private static final String HOME_PAGE = "pages/home.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        ProductDAO productDAO = new ProductDAO();
//        CategoryDAO categoryDAO = new CategoryDAO();
//        
////        List<Product> products = productDAO.findByPagination("all", 20, 0, 0, 0);
//        List<Product> products = productDAO.findTheHottest();
//        
//        List<ProductXMLWrapper> list = products.stream().map(p -> {
//            ProductXMLWrapper pWrapper = ObjectUtils.mapProductDTOToProductWrapper(p);
//            double avgVotes = votesDAO.findProductAVGVotes(p.getId());
//            pWrapper.setAvgVotes(avgVotes);
//            return pWrapper;
//        }).collect(Collectors.toList());
//        
//        for(int i = 0; i < list.size(); i++){
//            System.out.println("name: " + list.get(i).getName());
//        }
//        
//        ProductListXmlWrapper productsListXmlWrapper = new ProductListXmlWrapper();
//        productsListXmlWrapper.setProducts(list);
//        
//        try {
//            String xmlDoc = JAXBUtils.marshal(productsListXmlWrapper, ProductListXmlWrapper.class);
//            request.setAttribute("products", xmlDoc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        RequestDispatcher rd = request.getRequestDispatcher(HOME_PAGE);
        rd.forward(request, response);
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
