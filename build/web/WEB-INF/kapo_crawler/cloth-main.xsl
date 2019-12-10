<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="https://kapo.vn/"
                xmlns="https://kapo.vn/"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <!--<xsl:import href="cloth-category.xsl"/>-->
    
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
    
       <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default doc'"/>
        <xsl:param name="host" select="'Default host'"/>
        <xsl:apply-templates select="$doc//ul[@id='menu-primary-menu'][1]"/>
    </xsl:template>
    
    <xsl:template match="ul[@id='menu-primary-menu'][1]">
        <xsl:for-each select="./li">
            <xsl:if test="position() &lt; 4 and position() &gt; 2">
                <xsl:variable name="category" select="./a/text()"/>
                <xsl:variable name="categoryLink" select="./a/@href"/>
                <xsl:call-template name="CrawlCloth">
                    <xsl:with-param name="category" select="$category"/>
                    <xsl:with-param name="link" select="$categoryLink"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
    
    
    <xsl:template name="CrawlCloth">
        <xsl:param name="category" select="'Default category value'"/>
        <xsl:param name="link" select="'Default link value'"/>
        <xsl:variable name="rootDomain" select="'https://kapo.vn'" />
        <xsl:variable name="full-link" select="concat($rootDomain,$link)" />
        
        <xsl:variable name="Cloth-list" select="document($full-link)"/>
        
        <xsl:for-each select="$Cloth-list//ul[@class='products']/li">
            <xsl:variable name="Cloth-link" select=".//div[@class='product-header']/a/@href"/>
            <xsl:call-template name="CrawlClothDetail">
                <xsl:with-param name="doc" select="$Cloth-link"/>
                <xsl:with-param name="category" select="$category"/>
            </xsl:call-template>
        </xsl:for-each>
        
        <xsl:apply-templates select="$Cloth-list//a[contains(@class,'paging-next')]">
            <xsl:with-param name="category" select="$category"/>
            <xsl:with-param name="nextLink" select="$link"/>
        </xsl:apply-templates>
    </xsl:template>
    
    <xsl:template match="a[contains(@class,'paging-next')]">
        <xsl:param name="category" select="'Default category'"/>
        <xsl:variable name="full-link" select="@href"/>
        <xsl:call-template name="CrawlCloth">
            <xsl:with-param name="link" select="$full-link"/>
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
