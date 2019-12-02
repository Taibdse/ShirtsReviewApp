<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    
    <xsl:template name="CrawlClothDetail">
        <xsl:param name="doc" select="'Default doc value'"/>
        <xsl:param name="category" select="'Default main category value'"/>
        <xsl:variable name="rootDomain" select="'https://kapo.vn'" />
        <xsl:variable name="full-link" select="concat($rootDomain,$doc)" />
        <xsl:variable name="detail" select="document($full-link)"/>
        <xsl:variable name="cloth-info" select="$detail//div[@id='content']"/>
        
        <xsl:element name="cloth">
            <xsl:element name="name">
                <xsl:value-of select="$cloth-info//h1[@class='product_title']/text()" ></xsl:value-of>
            </xsl:element>
            
            <xsl:element name="colors">
                <xsl:value-of select="''"></xsl:value-of> 
            </xsl:element>
            
            <xsl:element name="description">
                <xsl:value-of select="''"></xsl:value-of> 
            </xsl:element>
            
            <xsl:element name="sizes">
                <xsl:value-of select="''"></xsl:value-of> 
            </xsl:element>
            
            <xsl:element name="price">
                <xsl:value-of select="$cloth-info//p[@class='price']/span/text()"></xsl:value-of>
            </xsl:element>
            
            <xsl:element name="image">
                <xsl:value-of select="$cloth-info//div[@id='imgView']/img/@src"></xsl:value-of> 
            </xsl:element>
            
            <xsl:element name="category">
                <xsl:value-of select="$category"></xsl:value-of> 
            </xsl:element>
            
            <xsl:element name="link">
                <xsl:value-of select="$full-link"></xsl:value-of> 
            </xsl:element>   
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
