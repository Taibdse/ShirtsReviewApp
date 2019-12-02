<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    
    <xsl:template name="CrawlClothDetail">
        <xsl:param name="doc" select="'Default doc value'"/>
        <xsl:param name="category" select="'Default main category value'"/>
        <xsl:variable name="rootDomain" select="'https://routine.vn'" />
        <xsl:variable name="full-link" select="concat($rootDomain,$doc)" />
        <xsl:variable name="detail" select="document($full-link)"/>
        <xsl:variable name="cloth-info" select="$detail//div[contains(@class,'product-detail-wrapper')]"/>
        
        <xsl:element name="cloth">
            <xsl:element name="name">
                <xsl:value-of select="$cloth-info//div[contains(@class,'product-title')]/h1/text()" ></xsl:value-of>
            </xsl:element>
            
            <xsl:element name="colors">
                <xsl:for-each select="$cloth-info//div[@class='title_color_footer']">
                    <xsl:value-of select="text()"></xsl:value-of> 
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each>
            </xsl:element>
            
            <xsl:element name="description">
                <xsl:for-each select="$cloth-info//div[contains(@class,'description-content')]//p">
                    <xsl:value-of select="text()"></xsl:value-of> 
                    <xsl:if test="position() != last()">.</xsl:if>
                </xsl:for-each>
            </xsl:element>
            
            <xsl:element name="sizes">
                <xsl:for-each select="$cloth-info//div[@id='variant-swatch-1']//div[contains(@class,'swatch-element')]">
                    <xsl:value-of select=".//span/text()"></xsl:value-of> 
                    <xsl:if test="position() != last()">, </xsl:if>
                </xsl:for-each>
            </xsl:element>
            
            <xsl:element name="price">
                <xsl:value-of select="$cloth-info//span[contains(@class, 'pro-price')]/text()"></xsl:value-of>
            </xsl:element>
            
            <xsl:element name="image">
                <xsl:value-of select="$cloth-info//img[contains(@class,'product-image-feature')]/@src"></xsl:value-of> 
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
