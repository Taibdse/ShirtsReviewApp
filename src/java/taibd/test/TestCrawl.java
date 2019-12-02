/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.test;

import taibd.crawler.CrawlerUltimate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import taibd.checker.XmlSyntaxChecker;

/**
 *
 * @author HOME
 */
public class TestCrawl {
    public static void main(String[] args) {
//System.out.println(System.getProperty("user.dir"));
   UUID uuid = UUID.randomUUID();
                String id = uuid.toString();
                System.out.println("id: " + id);
//        try {
//            //1. Run crawler
//            DOMResult rs = CrawlerUltimate.crawl("routine_crawler/input-cloths.xml", "routine_crawler/cloth-main.xsl");
//            //2. Init transformer
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer();
//            StreamResult sr = new StreamResult(new FileOutputStream("routine_crawler/output-cloths.xml"));
//            //3. Transform to XML file
//            transformer.transform(new DOMSource(rs.getNode()), sr);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TestCrawl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TransformerException ex) {
//            Logger.getLogger(TestCrawl.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
