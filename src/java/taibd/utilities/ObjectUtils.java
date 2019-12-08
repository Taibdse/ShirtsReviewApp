/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

import taibd.entity.ProductXMLWrapper;
import taibd.model.Product;

/**
 *
 * @author HOME
 */
public class ObjectUtils {
    public static ProductXMLWrapper mapProductDTOToProductWrapper(Product p){
        ProductXMLWrapper wrapper = new ProductXMLWrapper();
        wrapper.setId(p.getId());
        wrapper.setCategoryId(p.getCategoryId());
        wrapper.setColors(p.getColors());
        wrapper.setDescription(p.getDescription());
        wrapper.setName(p.getName());
        wrapper.setSizes(p.getSizes());
        wrapper.setImage(p.getImage());
        wrapper.setLink(p.getLink());
        wrapper.setPrice(p.getPrice());
        int views = p.getViews() != null ? p.getViews() : 0;
        wrapper.setViews(views);
        return wrapper;
    }
}
