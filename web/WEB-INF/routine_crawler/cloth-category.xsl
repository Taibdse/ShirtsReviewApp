<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:import href="cloth-detail.xsl"/>
   
    <xsl:template name="CrawlCategory">
        <xsl:param name="doc" select="'Default doc'"/>
        <xsl:param name="host" select="'Default host'"/>
        <xsl:apply-templates select="$doc//nav[contains(@class, 'main-nav')]/ul"/>
    </xsl:template>
    
    <xsl:template match="nav[contains(@class, 'main-nav')]/ul">
        <xsl:for-each select="./li">
            <xsl:if test="position() = 2">
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
    <!--
    <xsl:template match="div[@class='thanhvien_page']/a[contains(@class,'next')][not(contains(@href,'http'))]">
        <xsl:param name="category" select="'Default main category value'"/>
        <xsl:param name="nextLink" select="'Default next link value'"/>
        <xsl:variable name="short-link" select="@href"/>
        <xsl:variable name="full-link" select="concat($nextLink,$short-link)"/>
        <xsl:call-template name="CrawlCloth">
            <xsl:with-param name="link" select="$full-link"/>
            <xsl:with-param name="category" select="$category"/>
        </xsl:call-template>
    </xsl:template>-->
</xsl:stylesheet>
