/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import taibd.entity.ResponseXMLWrapper;
import taibd.entity.ProductListXmlWrapper;
import taibd.entity.ProductXMLWrapper;
import taibd.model.Product;
import taibd.model.ProductDAO;
import taibd.model.User;
import taibd.model.Vote;
import taibd.model.VotesDAO;
import taibd.utilities.JAXBUtils;
import taibd.utilities.ObjectUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "ProductsAPIServlet", urlPatterns = {"/api/products"})
public class ProductsAPIServlet extends HttpServlet {

    private final static ProductDAO productDAO = new ProductDAO();
    private final static VotesDAO votesDAO = new VotesDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String categoryId = request.getParameter("categoryId");
        int limit = Integer.parseInt(request.getParameter("limit"));
        int offset = Integer.parseInt(request.getParameter("offset"));
        int fromPrice = Integer.parseInt(request.getParameter("fromPrice"));
        int toPrice = Integer.parseInt(request.getParameter("toPrice"));
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        List<Product> products = productDAO.findByPagination(categoryId, limit, offset, fromPrice, toPrice);
        List<ProductXMLWrapper> list = products.stream().map(p -> {
            ProductXMLWrapper pWrapper = ObjectUtils.mapProductDTOToProductWrapper(p);
            
            double avgVotes = votesDAO.findProductAVGVotes(p.getId());
            pWrapper.setAvgVotes(avgVotes);
            
            Vote vote = votesDAO.findVoteByUsernameAndProductId(user.getUsername(), p.getId());
            int votes = 0;
            if(vote != null) votes = vote.getVotes() != null ? vote.getVotes() : 0;
            
            pWrapper.setVotes(votes);
            return pWrapper;
        }).collect(Collectors.toList());
        
        ProductListXmlWrapper wrapper = new ProductListXmlWrapper();
        wrapper.setProducts(list);
        String responseResult;
        
        
        try {
            responseResult = JAXBUtils.marshal(wrapper, ProductListXmlWrapper.class);
            response.getOutputStream().write(responseResult.getBytes(StandardCharsets.UTF_8));
        } catch (JAXBException ex) {
            Logger.getLogger(ProductsAPIServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
