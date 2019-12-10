package taibd.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import taibd.model.Product;
import taibd.model.User;
import taibd.model.VotePK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-10T14:55:25")
@StaticMetamodel(Vote.class)
public class Vote_ { 

    public static volatile SingularAttribute<Vote, Product> product;
    public static volatile SingularAttribute<Vote, Integer> votes;
    public static volatile SingularAttribute<Vote, VotePK> votePK;
    public static volatile SingularAttribute<Vote, User> user;

}