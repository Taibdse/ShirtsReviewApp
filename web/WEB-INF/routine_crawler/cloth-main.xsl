<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:x="https://routine.vn/"
                xmlns="https://routine.vn/"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:import href="cloth-category.xsl"/>
    
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
