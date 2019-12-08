<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns="http://sneakershop.vn"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">
    <xsl:output method="xml"/>
    
    <xsl:template match="text()"></xsl:template>
    
    <xsl:template match="/">
        <fo:root font-family="Calibri" xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4">
                    <fo:region-body margin="2cm" margin-top="3.5cm"></fo:region-body>
                    <fo:region-before></fo:region-before>
                    <fo:region-after></fo:region-after>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:table>
                        <!--                        <fo:table-column column-width="50%"></fo:table-column>
                        <fo:table-column column-width="50%"></fo:table-column>-->
                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block margin-bottom="1cm">
                                        <fo:external-graphic src="url({$root}/images/LogoMakr_9xUPyW.png)" content-width="30cm"  content-height="3.5cm"/>
                                    </fo:block>
                                </fo:table-cell>                          
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block margin-top="-1.5cm" text-align="center">
                        © 2019 Sneaker Shop Inc.
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="24pt">Danh sách giày được xem nhiều nhất</fo:block>
                    <fo:block>
                        <xsl:value-of select="$date"></xsl:value-of>
                    </fo:block>
                    <fo:table margin-top="0.5cm" border="solid 1px black" text-align="center">
                        <fo:table-column column-width="10%"></fo:table-column>
                        <fo:table-column column-width="20%"></fo:table-column>
                        <fo:table-column column-width="30%"></fo:table-column>
                        <fo:table-column column-width="15%"></fo:table-column>
                        <fo:table-column column-width="25%"></fo:table-column>
                        <fo:table-body>
                            <fo:table-row font-weight="bold">
                                <fo:table-cell>
                                    <fo:block>*</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Name</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Image</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Colors</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>sizes</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Price</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Stars</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>Views</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:apply-templates></xsl:apply-templates>
                        </fo:table-body>
                    </fo:table>
<!--                    <fo:block text-align="right">
                        Tổng lược xem: <xsl:value-of select="sum(//*[name()='view'])"></xsl:value-of>
                    </fo:block>
                    <fo:block>
                        Cảm ơn vì đã sử dụng dịch vụ của chúng tôi.
                    </fo:block>
                    <fo:block>
                        Liên hệ chúng tôi tại gmail : lamthanhphat98@gmail.com
                    </fo:block>
                    <fo:block margin="1cm" text-align="right">
                        <fo:block font-weight="bold" >SneakerShop.vn</fo:block>
                        <fo:block>Student: Lam Thanh Phat</fo:block>
                        <fo:block>ID: SE62882</fo:block>
                        <fo:block>Class: SE1268</fo:block>
                    </fo:block>-->
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="*[name()='report']">
        <fo:table-row border="solid 1px black">
            <fo:table-cell>
                <fo:block>
                    <xsl:number count="*[name()='report']"></xsl:number>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:variable name="image" select="*[name()='picture']"></xsl:variable>
                    <fo:external-graphic src="url({$image})" content-height="2cm"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="*[name()='name']"></xsl:value-of>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="format-number(*[name()='price'], '###,###')"></xsl:value-of> đ
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:variable name="view" select="*[name()='view']"></xsl:variable>
                    <xsl:value-of select="$view"></xsl:value-of>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>
