/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "Vote.findAll", query = "SELECT v FROM Vote v")
    , @NamedQuery(name = "Vote.findByUsername", query = "SELECT v FROM Vote v WHERE v.votePK.username = :username")
    , @NamedQuery(name = "Vote.findByProductId", query = "SELECT v FROM Vote v WHERE v.votePK.productId = :productId")
    , @NamedQuery(name = "Vote.findByVotes", query = "SELECT v FROM Vote v WHERE v.votes = :votes")})
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotePK votePK;
    @Column(name = "votes")
    private Integer votes;

    public Vote() {
    }

    public Vote(VotePK votePK) {
        this.votePK = votePK;
    }

    public Vote(String username, int productId) {
        this.votePK = new VotePK(username, productId);
    }

    public VotePK getVotePK() {
        return votePK;
    }

    public void setVotePK(VotePK votePK) {
        this.votePK = votePK;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votePK != null ? votePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vote)) {
            return false;
        }
        Vote other = (Vote) object;
        if ((this.votePK == null && other.votePK != null) || (this.votePK != null && !this.votePK.equals(other.votePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "taibd.model.Vote[ votePK=" + votePK + " ]";
    }
    
}
