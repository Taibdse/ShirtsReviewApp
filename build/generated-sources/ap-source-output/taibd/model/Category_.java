package taibd.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import taibd.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-10T23:34:42")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile CollectionAttribute<Category, Product> productCollection;
    public static volatile SingularAttribute<Category, String> name;
    public static volatile SingularAttribute<Category, String> id;
    public static volatile SingularAttribute<Category, String> slug;

}