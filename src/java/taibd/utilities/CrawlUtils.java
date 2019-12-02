/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import taibd.crawler.CrawlerUltimate;
import taibd.entity.JAXBProduct;
import taibd.model.Category;
import taibd.model.CategoryDAO;
import taibd.model.Product;
import taibd.model.ProductDAO;
import taibd.test.TestCrawl;

/**
 *
 * @author HOME
 */
public class CrawlUtils {
//    private final Session session;
//    private final CategoryRepository categoryRepository;
//    private final PlaceRepository placeRepository;
//    private final String TAG = "CrawlService: ";
//    private final String realPath;
//

    ServletContext context;

    public CrawlUtils(ServletContext context) {
        this.context = context;
    }

    public void crawlWebsite(String inputXmlPath, String xslPath, String outputXmlPath) throws IOException, JAXBException {
        FileOutputStream fo = null;
        ByteArrayOutputStream bos = null;
        try {
            System.out.println("inputXmlPath: " + inputXmlPath);
            System.out.println("xslPath: " + xslPath);

            bos = TrAXUtils.transform(inputXmlPath, xslPath);

//              save to file to test
//            fo = new FileOutputStream(outputXmlPath);
//            fo.write(bos.toByteArray());
//            fo.flush();
//            
//            InputStream fileInputstream = new FileInputStream(outputXmlPath);
            List<JAXBProduct> products = parseDataFromXMLByStAX(new ByteArrayInputStream(bos.toByteArray()));
//            List<JAXBProduct> products = parseDataFromXMLByStAX(fileInputstream);
            System.out.println("products: " + products.size());
            System.out.println("=====================");
            for (JAXBProduct p : products) {
                System.out.println("name: " + p.getName());
                System.out.println("desc: " + p.getDescription());
            }

//             validate 
            List<JAXBProduct> list = new ArrayList<>();
            for (JAXBProduct product : products) {
                if (isValid(product)) {
                    list.add(product);
                }
            }

            System.out.println("list: " + list.size());

            saveToDB(list);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCrawl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TestCrawl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(CrawlUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fo != null) {
                fo.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    public void saveToDB(List<JAXBProduct> products) {
        List<Category> categories = new ArrayList<>();
        HashMap<String, List<Product>> hashmap = new HashMap<>();

        products.stream().forEach(p -> {
            Product product = mapJAXBProductToProduct(p);
            if (hashmap.get(p.getCategory()) == null) {
                List<Product> list = new ArrayList<Product>();
                list.add(product);
                hashmap.put(p.getCategory(), list);
            } else {
                List<Product> list = hashmap.get(p.getCategory());
                list.add(product);
            }
        });

        CategoryDAO dao = new CategoryDAO();
        ProductDAO porductDao = new ProductDAO();

        for (Map.Entry entry : hashmap.entrySet()) {
            String categoryName = (String) entry.getKey();

            Category found = dao.getCategoryByMatchingName(categoryName);
            String cateId;
            if (found == null) {
                UUID uuid = UUID.randomUUID();
                cateId = uuid.toString();
                Category category = new Category();
                category.setName(categoryName);
                category.setId(cateId);
                dao.persist(category);
            } else {
                cateId = found.getId();
            }

            List<Product> list = (List<Product>) entry.getValue();

            for (Product p : list) {
                p.setId(0);
                p.setCategoryId(cateId);
                porductDao.persist(p);
            }
        }
    }

    public Product mapJAXBProductToProduct(JAXBProduct p) {
        Product product = new Product();
        product.setName(p.getName());
        product.setDescription(p.getDescription());
        product.setColors(p.getColors());
        product.setImage(p.getImage());
        product.setPrice(p.getPrice());
        product.setLink(p.getLink());
        product.setSizes(p.getSizes());
        return product;

    }

    private double getPriceFromString(String str) {
        if (str == null) {
            return 0;
        }
        str = str.replaceAll("\\D+", "");
        if (str.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(str);
    }

    private List<JAXBProduct> parseDataFromXMLByStAX(InputStream is) throws XMLStreamException {
        List<JAXBProduct> products = new ArrayList<>();
        XMLStreamReader reader = StAXUtils.getXMLStreamReader(is);
        JAXBProduct product = null;

        while (reader.hasNext()) {
            int currentCursor = reader.next();
            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                System.out.println("local name: " + reader.getLocalName());
                if (reader.getLocalName().trim().equals("cloth")) {

                    product = new JAXBProduct();
                    // get child node and create object placeCrawlDto
                    //name, colors, desc, sizes, price, image, category, link
                    String name = StAXUtils.getTextContentUsingStAXCursor("name", reader).trim();
                    String colors = StAXUtils.getTextContentUsingStAXCursor("colors", reader).trim();
                    String description = StAXUtils.getTextContentUsingStAXCursor("description", reader).trim();
                    String sizes = StAXUtils.getTextContentUsingStAXCursor("sizes", reader).trim();
                    String price = StAXUtils.getTextContentUsingStAXCursor("price", reader).trim();
                    String image = StAXUtils.getTextContentUsingStAXCursor("image", reader).trim();
                    String category = StAXUtils.getTextContentUsingStAXCursor("category", reader).trim();
                    String link = StAXUtils.getTextContentUsingStAXCursor("link", reader).trim();
//                    String link = "";

                    product.setName(name);
                    product.setColors(colors);
                    product.setDescription(description);
                    product.setSizes(sizes);
                    product.setImage(image);
                    product.setLink(link);
                    product.setPrice(getPriceFromString(price));
                    product.setCategory(category);

                }
            }

            if (currentCursor == XMLStreamConstants.END_ELEMENT) {
                if (reader.getLocalName().trim().equals("cloth")) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    private boolean isValid(JAXBProduct product) throws JAXBException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            ServletContext.class.get
            String realPath = context.getRealPath("/");
            String filepath = realPath + "WEB-INF/" + "schemas/cloth.xsd";
            Schema schema = schemaFactory.newSchema(new File(filepath));

            JAXBContext jAXBContext = JAXBContext.newInstance(JAXBProduct.class);
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(product, new DefaultHandler());
            return true;
        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            Logger.getLogger(CrawlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
