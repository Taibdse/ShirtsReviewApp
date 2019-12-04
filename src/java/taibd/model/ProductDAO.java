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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author HOME
 */
public class ProductDAO implements Serializable{

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
    
    public List<Product> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Product.findAll").getResultList();
    }
    
    public Product findById(int id){
        EntityManager em = emf.createEntityManager();
        return (Product) em.createNamedQuery("Product.findById").setParameter("id", id).getSingleResult();
    }
    
    public List<Product> findByPagination(int categoryId, int limit, int offset, double fromPrice, double toPrice){
        EntityManager em = emf.createEntityManager();
//        Query query = em.createQuery("SELECT p FROM Product p ORDER BY p.id OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY");
        String sql = "";

        boolean hasCategory = false;
        boolean hasPriceRange = false;
        
        if(categoryId > 0){
            sql = "SELECT p FROM Product p where categoryId = :categoryId";
            hasCategory = true;
        } else {
            sql = "SELECT p FROM Product p";
        }
        
        if(fromPrice > 0 && toPrice > 0){
            hasPriceRange = true;
            if(hasCategory) sql += " AND (p.price BETWEEN :fromPrice and :toPrice)";
            else sql += " WHERE (p.price BETWEEN :fromPrice and :toPrice)";
        }
        
        Query query = em.createQuery(sql);
        sql += " ORDER by id DESC";
        
        if(hasCategory) query.setParameter("categoryId", categoryId);
        if(hasPriceRange){
            query.setParameter("fromPrice", fromPrice);
            query.setParameter("toPrice", toPrice);
        }
        
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        
        return query.getResultList();
        
    }
}
