package taibd.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import taibd.model.Category;
import taibd.model.Vote;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-10T23:34:42")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> image;
    public static volatile CollectionAttribute<Product, Vote> voteCollection;
    public static volatile SingularAttribute<Product, String> sizes;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> link;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, String> colors;
    public static volatile SingularAttribute<Product, String> slug;
    public static volatile SingularAttribute<Product, Integer> views;
    public static volatile SingularAttribute<Product, Category> categoryId;

}