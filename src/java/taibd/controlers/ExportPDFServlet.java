/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import taibd.common.Constants;
import taibd.entity.ProductListXmlWrapper;
import taibd.entity.ProductXMLWrapper;
import taibd.model.CategoryDAO;
import taibd.model.Product;
import taibd.model.ProductDAO;
import taibd.model.User;
import taibd.model.VotesDAO;
import taibd.utilities.JAXBUtils;
import taibd.utilities.ObjectUtils;
import taibd.utilities.PDFUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "ExportPDFServlet", urlPatterns = {"/exportPDF"})
public class ExportPDFServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        VotesDAO votesDAO = new VotesDAO();

        //Synthesis data
        ProductListXmlWrapper productsListXmlWrapper = productDAO.findTheHottest();

        if (productsListXmlWrapper.getProducts().size() > 0) {
            try {
                String xmlDoc = JAXBUtils.marshal(productsListXmlWrapper, ProductListXmlWrapper.class);
                request.setAttribute("products", xmlDoc);

                Date time = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("E MM/dd/yyyy 'at' hh:mm:ss a");
                //co nghe noi ko, t ko nghe gi het
                String fo = PDFUtils.transformFromXSL(getServletContext().getRealPath("/WEB-INF/pdf/hot_products.xsl"), xmlDoc, new PDFUtils.CustomizeTransformerCallback() {
                    @Override
                    public void customize(Transformer trans) {
                        trans.setParameter("timestamp", ft.format(time));
                        trans.setParameter("root", getServletContext().getRealPath("/"));
                    }
                });

                PDFUtils.exportXMLtoPDF(fo, request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
