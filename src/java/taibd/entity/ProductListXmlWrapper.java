/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import taibd.model.Product;

/**
 *
 * @author HOME
 */
@XmlRootElement(name = "products")
public class ProductListXmlWrapper {
    List<ProductXMLWrapper> products;

    public ProductListXmlWrapper() {
    }

    @XmlElement(name = "product")
    public List<ProductXMLWrapper> getProducts() {
        return products;
    }

    public void setProducts(List<ProductXMLWrapper> products) {
        this.products = products;
    }
    
}
