<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : hot_products.xsl
    Created on : December 10, 2019, 11:38 PM
    Author     : HOME
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns="http://hotpshirt.vn"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                version="1.0">
    <xsl:output method="xml"/>
    <xsl:param name="timestamp"></xsl:param>
    <xsl:param name="root"></xsl:param>
    
    <xsl:template match="text()"></xsl:template>
    
    <xsl:template match="/">
        <fo:root font-family="Calibri">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4">
                    <fo:region-body margin="2cm" margin-top="2.5cm"></fo:region-body>
                    <fo:region-before></fo:region-before>
                    <fo:region-after></fo:region-after>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
<!--                <fo:static-content flow-name="xsl-region-before">
                   
                </fo:static-content>-->
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="24pt" text-align="center">The most popular shirts of ShirtsReview</fo:block>
                    <fo:block text-align="center" margin-top="0.5cm">
                        Created Time: <xsl:value-of select="$timestamp"></xsl:value-of>
                    </fo:block>
                    <fo:table margin-top="0.5cm" border="solid 1px black" text-align="center">
                        <fo:table-column column-width="5%"></fo:table-column>
                        <fo:table-column column-width="15%"></fo:table-column>
                        <fo:table-column column-width="15%"></fo:table-column>
                        <fo:table-column column-width="10%"></fo:table-column>
                        <fo:table-column column-width="15%"></fo:table-column>
                        <fo:table-column column-width="7%"></fo:table-column>
                        <fo:table-column column-width="7%"></fo:table-column>
                        <fo:table-column column-width="26%"></fo:table-column>
                        <!--<fo:table-column column-width="20%"></fo:table-column>-->
<!--                        <fo:table-column column-width="10%"></fo:table-column>
                        <fo:table-column column-width="20%"></fo:table-column>-->
                        <fo:table-body>
                            <fo:table-row font-weight="bold">
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>*</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Name</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Price</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Colors</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Sizes</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Stars</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Views</fo:block>
                                </fo:table-cell>
                                
                                <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                                    <fo:block>Link</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:apply-templates></xsl:apply-templates>
                        </fo:table-body>
                    </fo:table>
                    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="*[name()='product']">
        <fo:table-row border="solid 1px black">
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                    <!--<xsl:number count="position()"></xsl:number>-->
                    <xsl:value-of select="position()" />
                </fo:block>
            </fo:table-cell>
            
           <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                    <!--<xsl:value-of select="*[name()='name']"></xsl:value-of>-->
                    <xsl:value-of select="./name/text()"></xsl:value-of>
                </fo:block>
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                    <xsl:value-of select='format-number(.//price/text(), "###,###")' /> vnd
                </fo:block>
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                     <xsl:value-of select="./colors/text()"></xsl:value-of>
                </fo:block>
               <!--font-weight="bold" font-size="18pt" color="blueviolet"-->
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                     <xsl:value-of select="./sizes/text()"></xsl:value-of>
                </fo:block>
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                    <xsl:value-of select="./avgVotes/text()"></xsl:value-of>
                </fo:block>
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block>
                    <xsl:value-of select="./views/text()"></xsl:value-of>
                </fo:block>
                
            </fo:table-cell>
            
            <fo:table-cell border="solid 1px black" padding-top="0.2cm" padding-bottom="0.2cm">
                <fo:block color="blue">
                    <xsl:variable name="link" select=".//link/text()" />
                    <fo:basic-link external-destination="{$link}">
                        <xsl:value-of select="'click here'"></xsl:value-of>
                    </fo:basic-link>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>

