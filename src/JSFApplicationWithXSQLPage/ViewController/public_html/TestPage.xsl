<?xml version="1.0"?>
<!--
 | smuench 08/24/02 - Make underscores show as spaces in headings
 +-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="css/TestPage.css"/>
      </head>
      <body class="page">
        <xsl:apply-templates select="page"/>
      </body>
    </html>
  </xsl:template>
  <xsl:template match="*[*[ends-with(local-name(),'Row')]]">
    <center>
      <table border="0" cellpadding="4">
        <xsl:choose>
          <xsl:when test="*[ends-with(local-name(),'Row')]">
            <xsl:for-each select="*[ends-with(local-name(),'Row')][1]">
              <tr>
                <xsl:for-each select="*[not(starts-with(name(.),'H_'))]">
                  <th align="left">
                    <xsl:attribute name="class">
                      <xsl:choose>
                        <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
                        <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
                      </xsl:choose>
                    </xsl:attribute>
                    <xsl:value-of select="translate(name(.),'_',' ')"/>
                  </th>
                </xsl:for-each>
              </tr>
            </xsl:for-each>
            <xsl:apply-templates/>
          </xsl:when>
          <xsl:otherwise>
            <tr>
              <td>No Matches</td>
            </tr>
          </xsl:otherwise>
        </xsl:choose>
      </table>
    </center>
  </xsl:template>
  <xsl:template match="*[ends-with(local-name(),'Row')]">
    <tr>
      <xsl:attribute name="class">
        <xsl:choose>
          <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
          <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
        </xsl:choose>
      </xsl:attribute>
      <xsl:for-each select="*[not(starts-with(name(.),'H_'))]">
        <td>
          <xsl:attribute name="class">
            <xsl:choose>
              <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
              <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
            </xsl:choose>
          </xsl:attribute>
          <xsl:apply-templates select="."/>
        </td>
      </xsl:for-each>
    </tr>
  </xsl:template>
</xsl:stylesheet>

