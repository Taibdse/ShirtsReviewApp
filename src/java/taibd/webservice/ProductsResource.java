/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.webservice;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import taibd.entity.ProductListXmlWrapper;
import taibd.model.Product;
import taibd.model.ProductDAO;

/**
 * REST Web Service
 *
 * @author HOME
 */
@Path("def")
public class ProductsResource {

    @Context
    private UriInfo context;

   
    public ProductsResource() {
    }
    
    @GET
//    @Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public ProductListXmlWrapper getProducts(
            @QueryParam("categoryId") String categoryId, 
            @QueryParam("fromPrice") double fromPrice, 
            @QueryParam("toPrice") double toPrice, 
            @QueryParam("limit") int limit, 
            @QueryParam("offset") int offset) {
        //TODO return proper representation object
//        ProductDAO productDAO = new ProductDAO();
//        List<Product> products = productDAO.findByPagination(categoryId, limit, offset, fromPrice, toPrice);
//        ProductListXmlWrapper wrapper = new ProductListXmlWrapper();
//        wrapper.setProducts(products);
//        return wrapper;
        return null;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
