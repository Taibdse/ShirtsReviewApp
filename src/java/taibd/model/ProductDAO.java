/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.model;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import taibd.entity.ProductListXmlWrapper;
import taibd.entity.ProductXMLWrapper;
import taibd.utilities.ObjectUtils;
import taibd.utilities.StringUtils;

/**
 *
 * @author HOME
 */
public class ProductDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShirtReviewsAppPU");

    public void persist(Product object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Product.findAll").getResultList();
    }

    public Product findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (Product) em.createNamedQuery("Product.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean updateProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "UPDATE Product p SET p.views = :views WHERE p.id = :id";
            Query query = em.createQuery(sql);
            query.setParameter("views", product.getViews());
            query.setParameter("id", product.getId());
            em.getTransaction().begin();
            int result = query.executeUpdate();
            em.getTransaction().commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> findByPagination(String categoryId, int limit, int offset, double fromPrice, double toPrice) {
        EntityManager em = emf.createEntityManager();
//        Query query = em.createQuery("SELECT p FROM Product p ORDER BY p.id OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY");
        String sql = "";

        boolean hasCategory = false;
        boolean hasPriceRange = false;

//        categoryId = "all";
        if (categoryId == null) {
            categoryId = "all";
        }

        if (!categoryId.equalsIgnoreCase("all")) {
            sql = "SELECT p FROM Product p where p.categoryId.id = :categoryId";
            hasCategory = true;
        } else {
            sql = "SELECT p FROM Product p";
        }

        if (fromPrice > 0 && toPrice > 0) {
            hasPriceRange = true;
            if (hasCategory) {
                sql += " AND (p.price BETWEEN :fromPrice and :toPrice)";
            } else {
                sql += " WHERE (p.price BETWEEN :fromPrice and :toPrice)";
            }
        }

        Query query = em.createQuery(sql);
        sql += " ORDER by id DESC";
        System.out.println("sql:" + sql);

        if (hasCategory) {
            query.setParameter("categoryId", categoryId);
        }
        if (hasPriceRange) {
            query.setParameter("fromPrice", fromPrice);
            query.setParameter("toPrice", toPrice);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    public ProductListXmlWrapper findTheHottest() {
        EntityManager em = emf.createEntityManager();
//        String sql = "SELECT p FROM Product p WHERE "
//                + "(select AVG(v.votes) from Vote v where v.votePK.productId = p.id) >= 3.5 "
//                + "ORDER BY (select COUNT(v.votePK.productId) from Vote v where v.votePK.productId = p.id) desc";

        String sql = "SELECT p FROM Product p WHERE "
                + "(select AVG(CAST(v.votes as float)) from Vote v where v.votePK.productId = p.id) >= 3.5 ";
        Query query = em.createQuery(sql);
        
//        query.setFirstResult(0);
//        query.setMaxResults(15);

        List<Product> products = query.getResultList();
        VotesDAO votesDAO = new VotesDAO();
        
        List<ProductXMLWrapper> list = products.stream().map(p -> {
            ProductXMLWrapper pWrapper = ObjectUtils.mapProductDTOToProductWrapper(p);
            double avgVotes = votesDAO.findProductAVGVotes(p.getId());
            int numOfVotes = votesDAO.findProductCountVotes(p.getId());
            pWrapper.setAvgVotes(avgVotes);
            pWrapper.setNumOfVotes(numOfVotes);
            pWrapper.setSizes(StringUtils.getValidString(pWrapper.getSizes()));
            pWrapper.setColors(StringUtils.getValidString(pWrapper.getColors()));
            return pWrapper;
        })
        .sorted((p1, p2) -> p2.getNumOfVotes() - p1.getNumOfVotes())
        .skip(0)
        .limit(15)
        .collect(Collectors.toList());

        ProductListXmlWrapper productsListXmlWrapper = new ProductListXmlWrapper();
        productsListXmlWrapper.setProducts(list);
        
        return productsListXmlWrapper;
    }
}
