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

    public static Object unmarshal(Node node, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller u = context.createUnmarshaller();
        return u.unmarshal(new DOMSource(node));
    }

    public static Object unmarshal(InputStream is, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(is);
    }
    
    public static Object unmarshal(Reader reader, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(reader);
    }

    public static Object unmarshalBoardgame(InputStream is,
            ValidationEventHandler handler, Schema schema, Class classType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(handler);
        return unmarshaller.unmarshal(is);
    }

    public static void marshal(OutputStream os, Object data, Class type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(data, os);
    }

    public static String marshal(Object data, Class type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter writer = new StringWriter();
        m.marshal(data, writer);
        return writer.toString();
    }

//    public static void generateJavaClass(String output, String packageName, String schemaFilePath) throws IOException {
//        SchemaCompiler sc = XJC.createSchemaCompiler();
//        sc.setErrorListener(new ConsoleErrorReporter());
//
//        sc.forcePackageName(packageName);
//
//        File f = new File(schemaFilePath);
//        sc.parseSchema(new InputSource(f.toURI().toString()));
//
//        S2JJAXBModel model = sc.bind();
//        JCodeModel code = model.generateCode(null, null);
//        code.build(new File(output));
//    }

}
