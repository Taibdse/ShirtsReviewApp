/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HOME
 */
@Entity
@Table(name = "Votes", catalog = "ClothsReview", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Votes.findAll", query = "SELECT v FROM Votes v")
    , @NamedQuery(name = "Votes.findById", query = "SELECT v FROM Votes v WHERE v.id = :id")
    , @NamedQuery(name = "Votes.findByUsername", query = "SELECT v FROM Votes v WHERE v.username = :username")
    , @NamedQuery(name = "Votes.findByProductId", query = "SELECT v FROM Votes v WHERE v.productId = :productId")
    , @NamedQuery(name = "Votes.findByVotes", query = "SELECT v FROM Votes v WHERE v.votes = :votes")
    , @NamedQuery(name = "Votes.findByViews", query = "SELECT v FROM Votes v WHERE v.views = :views")})
public class Votes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", length = 500)
    private String username;
    @Column(name = "productId")
    private Integer productId;
    @Column(name = "votes")
    private Integer votes;
    @Column(name = "views")
    private Integer views;

    public Votes() {
    }

    public Votes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votes)) {
            return false;
        }
        Votes other = (Votes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "taibd.model.Votes[ id=" + id + " ]";
    }
    
}
