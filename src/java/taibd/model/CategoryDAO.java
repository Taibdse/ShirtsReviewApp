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
import javax.persistence.Persistence;
import taibd.utilities.StringUtils;

/**
 *
 * @author HOME
 */
public class CategoryDAO implements Serializable{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShirtReviewsAppPU");

    public void persist(Category object) {
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
    
    public List<Category> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Category.findAll").getResultList();
    }
    
    public Category getCategoryByMatchingName(String categoryName){
        List<Category> list = findAll();
        List<Category> foundList = list.stream().filter(cate -> {
            return StringUtils.computeMatchingPercent(categoryName, cate.getName()) >= 80;
        }).collect(Collectors.toList());
        if(foundList.size() == 0) return null;
        return foundList.get(0);
    }
}
