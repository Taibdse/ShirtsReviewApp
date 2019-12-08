/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import taibd.utilities.StringUtils;

/**
 *
 * @author HOME
 */
@XmlRootElement(name = "product")
@XmlType(name = "product", propOrder = {
    "id",
    "name",
    "colors",
    "description",
    "sizes",
    "price",
    "image",
    "categoryId",
    "link",
    "views",
    "votes",
    "avgVotes",
    "numOfVotes",
})
public class ProductXMLWrapper {
    private int id;
    private String name;
    private String colors;
    private String description;
    private String sizes;
    private double price;
    private String image;
    private String categoryId;
    private String link;
    private int views;
    private int votes;
    private double avgVotes;
    private int numOfVotes;

    public ProductXMLWrapper() {
    }
    

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    @XmlElement
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlElement
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @XmlElement
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @XmlElement
    public double getAvgVotes() {
        return avgVotes;
    }

    public void setAvgVotes(double avgVotes) {
        this.avgVotes = avgVotes;
    }

    @XmlElement
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @XmlElement
    public int getNumOfVotes() {
        return numOfVotes;
    }
    
    public void setNumOfVotes(int numOfVotes) {
        this.numOfVotes = numOfVotes;
    }
    
    public String getPriceFormatted(){
        return StringUtils.getPriceFormat(price);
    }
    
}
