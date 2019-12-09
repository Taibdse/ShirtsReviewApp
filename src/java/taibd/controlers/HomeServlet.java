/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.controlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;
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
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

//        List<Product> products = productDAO.findByPagination("all", 20, 0, 0, 0);
        
        //Synthesis data
        List<Product> products = productDAO.findTheHottest();

        List<ProductXMLWrapper> list = products.stream().map(p -> {
            ProductXMLWrapper pWrapper = ObjectUtils.mapProductDTOToProductWrapper(p);
            double avgVotes = votesDAO.findProductAVGVotes(p.getId());
            int numOfVotes = votesDAO.findProductCountVotes(p.getId());
            System.out.println("numOfVotes: " + numOfVotes);
            pWrapper.setAvgVotes(avgVotes);
            pWrapper.setNumOfVotes(numOfVotes);
            return pWrapper;
        })
        .sorted((p1, p2) -> p2.getNumOfVotes() - p1.getNumOfVotes())
        .skip(0)
        .limit(15)
        .collect(Collectors.toList());
        

        ProductListXmlWrapper productsListXmlWrapper = new ProductListXmlWrapper();
        productsListXmlWrapper.setProducts(list);

        try {
            String xmlDoc = JAXBUtils.marshal(productsListXmlWrapper, ProductListXmlWrapper.class);
            request.setAttribute("products", xmlDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        exportXMLtoPDF("WEB-INF/pdf/products.xml", )

        RequestDispatcher rd = request.getRequestDispatcher(HOME_PAGE);
        rd.forward(request, response);
    }

    private void exportXMLtoPDF(String filePath, String fo, HttpServletRequest request, HttpServletResponse response) throws FOPException, IOException, TransformerException, SAXException {
        response.setContentType("application/pdf;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        FopFactory ffactory = FopFactory.newInstance();
        ffactory.setUserConfig(filePath + "WEB-INF/xml/pdf.xml"); // pdf format
        FOUserAgent fua = ffactory.newFOUserAgent();
        Fop fop = ffactory.newFop(MimeConstants.MIME_PDF, fua, out); // configure FOP
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();
        StringReader reader = new StringReader(fo);
        StreamSource source = new StreamSource(reader);
        SAXResult result = new SAXResult(fop.getDefaultHandler()); //create resulting SAX events (the generated FO) must be piped through to
        trans.transform(source, result);
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
