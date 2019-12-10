<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="https://routine.vn/"
                xmlns="https://routine.vn/"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" />
    <!--<xsl:import href="cloth-category.xsl" />-->
    
    <!--<xsl:import href="cloth-detail.xsl"/>-->
    
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
   
    <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default doc'"/>
        <xsl:param name="host" select="'Default host'"/>
        <xsl:apply-templates select="$doc//nav[contains(@class, 'main-nav')]/ul"/>
    </xsl:template>
    
    <xsl:template match="nav[contains(@class, 'main-nav')]/ul">
        <xsl:for-each select="./li">
            <xsl:if test="position() &lt; 6">
                <xsl:variable name="category" select="./a/@title"/>
                <xsl:variable name="categoryLink" select="./a/@href"/>
                <xsl:call-template name="CrawlCloth">
                    <xsl:with-param name="category" select="$category"/>
                    <xsl:with-param name="link" select="$categoryLink"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
    
    
    <xsl:template name="CrawlCloth">
        <xsl:param name="category" select="'Default main category value'"/>
        <xsl:param name="link" select="'Default link value'"/>
        <xsl:variable name="rootDomain" select="'https://routine.vn'" />
        <xsl:variable name="full-link" select="concat($rootDomain,$link)" />
        
        <xsl:variable name="Cloth-list" select="document($full-link)"/>
        
        <xsl:for-each select="$Cloth-list//div[contains(@class,'content-product-list product-list')]//div[contains(@class, 'product-block')]">
            <xsl:variable name="Cloth-link" select=".//div[contains(@class, 'product-img')]/a/@href"/>
            <xsl:call-template name="CrawlClothDetail">
                <xsl:with-param name="doc" select="$Cloth-link"/>
                <xsl:with-param name="category" select="$category"/>
            </xsl:call-template>
        </xsl:for-each>
        
        <xsl:apply-templates select="$Cloth-list//a[contains(@class,'next')]">
            <xsl:with-param name="category" select="$category"/>
            <xsl:with-param name="nextLink" select="$link"/>
        </xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="a[contains(@class,'next')]">
        <xsl:param name="category" select="'Default category value'"/>
        <xsl:variable name="link" select="@href"/>
        <xsl:call-template name="CrawlCloth">
            <xsl:with-param name="link" select="$link"/>
            <xsl:with-param name="category" select="$category"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="x:cloths">
        <cloths>
            <xsl:variable name="doc" select="document(@link)"/>
            <xsl:variable name="host" select="@host"/>
            <xsl:call-template name="CrawlCategory">
                <xsl:with-param name="doc" select="$doc"/>
                <xsl:with-param name="host" select="$host"/>
            </xsl:call-template>
        </cloths>
    </xsl:template>
    
</xsl:stylesheet>
