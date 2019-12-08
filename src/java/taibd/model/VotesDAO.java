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
public class VotesDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShirtReviewsAppPU");

    public boolean persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return false;
    }

    public Vote findVoteByUsernameAndProductId(String username, int productId) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "SELECT v FROM Vote v WHERE v.votePK.username = :username AND v.votePK.productId = :productId";
            Query query = em.createQuery(sql);
            query.setParameter("username", username);
            query.setParameter("productId", productId);
            return (Vote) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
    
    public double findProductAVGVotes(int productId){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT v.votes FROM Vote v WHERE v.votePK.productId = :productId");
            query.setParameter("productId", productId);
            List<Integer> list = query.getResultList();
            if(list.size() == 0) return 0;
            int sum = 0;
            for(Integer item: list){
                sum += item;
            }
            return (double) Math.round(((double) sum/list.size()) * 100) / 100 ;
//            return (double) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
     public int findProductCountVotes(int productId){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.votePK.productId = :productId");
            query.setParameter("productId", productId);
            return (int) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
   

    public boolean addVote(Vote vote) {
        return persist(vote);
    }

    public boolean updateVote(Vote vote) {
        EntityManager em = emf.createEntityManager();
        try {
            String sql = "UPDATE Vote v set v.votes = :votes WHERE v.votePK.username = :username AND v.votePK.productId = :productId";
            Query query = em.createQuery(sql);
            query.setParameter("votes", vote.getVotes());
            query.setParameter("username", vote.getVotePK().getUsername());
            query.setParameter("productId", vote.getVotePK().getProductId());
            em.getTransaction().begin();
            int result = query.executeUpdate();
            em.getTransaction().commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }
}
