/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import taibd.common.Constants;
import taibd.utilities.CrawlUtils;

/**
 *
 * @author HOME
 */
@WebServlet(name = "CrawlerServlet", urlPatterns = {"/CrawlerServlet"})
public class CrawlerServlet extends HttpServlet {

    private static final String CRAWL_PAGE = "pages/crawl.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//
//        PrintWriter out = response.getWriter();
//        RequestDispatcher rd = request.getRequestDispatcher(CRAWL_PAGE);
//
//        rd.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher(CRAWL_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String btAction = request.getParameter("btAction");
        
        String inputXml = "";
        String xslPath = "";
        String outputXml = "";
        ServletContext context = this.getServletContext();
        String realPath = context.getRealPath("/");
        String prefix = realPath + "WEB-INF/";
        
        try {
            if (btAction.contains("routine.vn")) {
                inputXml = prefix + Constants.ROUTINE_INPUT_XML;
                xslPath = prefix + Constants.ROUTINE_XSL;
                outputXml = prefix + Constants.ROUTINE_OUTPUT_XML;
            } else if (btAction.contains("kapo.vn")) {
                inputXml = prefix + Constants.KAPO_INPUT_XML;
                xslPath = prefix + Constants.KAPO_XSL;
                outputXml = prefix + Constants.KAPO_OUTPUT_XML;
            }
        } finally {
            if(!inputXml.isEmpty()){
                try {
                    CrawlUtils CrawlUtils = new CrawlUtils(this.getServletContext());
                    CrawlUtils.crawlWebsite(inputXml, xslPath, outputXml);
                    out.write("Success");
                } catch (JAXBException ex) {
                    Logger.getLogger(CrawlerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
