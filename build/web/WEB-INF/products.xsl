<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : homePage.xsl
    Created on : December 8, 2019, 10:30 AM
    Author     : HOME
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/products">
        <table class="table table-striped table responsive text-center">
            <thead>
                <tr>
                    <th>*</th>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Colors</th>
                    <th>Sizes</th>
                    <th>Price</th>
                    <th>Stars</th>
                    <th>Views</th>
                    <th>Link</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="//product">
                    <tr>
                        <td>
                            <xsl:value-of select="position()" />
                        </td>
                        <td>
                            <xsl:value-of select=".//name/text()" />
                        </td>
                        <td>
                            <xsl:element name="img">
                                <xsl:attribute name="src">
                                    <xsl:value-of select=".//image/text()"/>
                                </xsl:attribute>
                                <xsl:attribute name="class">
                                    <xsl:value-of select="'img-fluid img-product'"/>
                                </xsl:attribute>
                             </xsl:element>
                        </td>
                        <td>
                            <xsl:value-of select=".//colors/text()" />
                        </td>
                        <td>
                            <xsl:value-of select=".//sizes/text()" />
                        </td>
                        <td>
                            <xsl:value-of select='format-number(.//price/text(), "###,###")' /> vnd
                        </td>
                        <td>
                            <xsl:value-of select=".//avgVotes/text()" /> <span class="fa fa-star star-rating checked"></span>
                            <div>
                                <xsl:value-of select=".//numOfVotes/text()" /> votes
                            </div>
                        </td>
                        <td>
                            <xsl:value-of select=".//views/text()" />
                        </td>
                        <td>
                            <xsl:element name="a">
                                <xsl:attribute name="class">
                                    <xsl:value-of select="'btn btn-primary'"/>
                                </xsl:attribute>
                                <xsl:attribute name="href">
                                     <xsl:value-of select=".//link/text()" />
                                </xsl:attribute>
                                <xsl:attribute name="target">
                                     <xsl:value-of select="'_blank'" />
                                </xsl:attribute>
                                <xsl:value-of select="'Go to buy'" />
                            </xsl:element>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

</xsl:stylesheet>
