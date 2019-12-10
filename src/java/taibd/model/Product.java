/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HOME
 */
@Entity
@Table(name = "Product", catalog = "ClothsReview", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id")
    , @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")
    , @NamedQuery(name = "Product.findBySlug", query = "SELECT p FROM Product p WHERE p.slug = :slug")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description")
    , @NamedQuery(name = "Product.findByImage", query = "SELECT p FROM Product p WHERE p.image = :image")
    , @NamedQuery(name = "Product.findByColors", query = "SELECT p FROM Product p WHERE p.colors = :colors")
    , @NamedQuery(name = "Product.findBySizes", query = "SELECT p FROM Product p WHERE p.sizes = :sizes")
    , @NamedQuery(name = "Product.findByLink", query = "SELECT p FROM Product p WHERE p.link = :link")
    , @NamedQuery(name = "Product.findByViews", query = "SELECT p FROM Product p WHERE p.views = :views")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;
    @Size(max = 500)
    @Column(name = "slug", length = 500)
    private String slug;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price", precision = 53)
    private Double price;
    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;
    @Size(max = 500)
    @Column(name = "image", length = 500)
    private String image;
    @Size(max = 500)
    @Column(name = "colors", length = 500)
    private String colors;
    @Size(max = 500)
    @Column(name = "sizes", length = 500)
    private String sizes;
    @Size(max = 500)
    @Column(name = "link", length = 500)
    private String link;
    @Column(name = "views")
    private Integer views;
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @ManyToOne
    private Category categoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<Vote> voteCollection;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @XmlTransient
    public Collection<Vote> getVoteCollection() {
        return voteCollection;
    }

    public void setVoteCollection(Collection<Vote> voteCollection) {
        this.voteCollection = voteCollection;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "taibd.model.Product[ id=" + id + " ]";
    }
    
}
