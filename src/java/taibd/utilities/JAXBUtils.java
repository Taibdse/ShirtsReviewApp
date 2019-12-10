/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author PhuNDSE63159
 */
public class JAXBUtils {

    public static String marshal(Object data, Class type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter writer = new StringWriter();
        m.marshal(data, writer);
        return writer.toString();
    }

}
