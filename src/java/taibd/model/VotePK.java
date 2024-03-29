/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HOME
 */
@Embeddable
public class VotePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "username", nullable = false, length = 500)
    private String username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "productId", nullable = false)
    private int productId;

    public VotePK() {
    }

    public VotePK(String username, int productId) {
        this.username = username;
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotePK)) {
            return false;
        }
        VotePK other = (VotePK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "taibd.model.VotePK[ username=" + username + ", productId=" + productId + " ]";
    }
    
}
