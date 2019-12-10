/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

/**
 *
 * @author HOME
 */
public class PDFUtils {
       public static String transform(String xslpath, String xmlContent) {
        return transformFromXSL(xslpath, xmlContent, null);
    }
    
    public static String transformFromXSL(String xslpath, String xmlContent, CustomizeTransformerCallback callback) {
        try {
            StringReader reader = new StringReader(xmlContent);
            StringWriter writer = new StringWriter();
            StreamSource src = new StreamSource(reader);
            StreamResult res = new StreamResult(writer);
            StreamSource xsl = new StreamSource(xslpath);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer(xsl);
            if (callback != null) {
                callback.customize(trans);
            }
            trans.transform(src, res);
            return res.getWriter().toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static interface CustomizeTransformerCallback {
        public void customize(Transformer trans);
    }
    
    public static void exportXMLtoPDF(String fo, HttpServletRequest request, HttpServletResponse response) throws FOPException, IOException, TransformerException, SAXException {
         response.setContentType("application/pdf;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        FopFactory ff = FopFactory.newInstance();
        ff.setUserConfig(request.getServletContext().getRealPath("/WEB-INF/pdf/hot_products.xml"));
        FOUserAgent fua = ff.newFOUserAgent();
        Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, out);

        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();
        StringReader reader = new StringReader(fo);
        StreamSource source = new StreamSource(reader);
        SAXResult result = new SAXResult(fop.getDefaultHandler());
        trans.transform(source, result);
    }
    
}
