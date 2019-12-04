/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.resolver;

import taibd.utilities.HttpUtils;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import taibd.checker.XmlSyntaxChecker;

/**
 *
 * @author HOME
 */
public class MyURIResolver implements javax.xml.transform.URIResolver {

    private int count = 0;

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null && (href.indexOf("https://routine.vn/") == 0 || href.indexOf("https://kapo.vn/") == 0)) {
            try {
                String content = HttpUtils.getHttpContent(href);
                XmlSyntaxChecker checker = new XmlSyntaxChecker();
                content = checker.refineHtml(content);
                content = checker.check(content);
                
//                BufferedWriter bw = new BufferedWriter(new FileWriter("src" + count + ".xml"));
//                bw.write(content);
//                bw.close();
                
                InputStream is = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
                System.out.println("Count: " + ++count + " Href: " + href);
                return new StreamSource(is);
            } catch (IOException ex) {
                Logger.getLogger(MyURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
