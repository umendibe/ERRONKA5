<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="index.css"/>
      </head>
      <body>
        <header>
          <img src="img/image.jpg"/>
          <h1 class="izenburua">GURUTZE GORRIA</h1>
        </header>
        <table border="1">
          <tr bgcolor="grey">
            <th>Enpresaren Izena</th>
            <th>IFK</th>
            <th>Data</th>
            <th>Mota</th>
            <th>Erreferentzia</th>
            <th>Izena</th>
            <th>Kopuru</th>
            <th>Manufak. Enpresa</th>
            <th>Iraungitze Data</th>
            <th>Kontserba</th>
            <th>Hoztu</th>
          </tr>
          <xsl:for-each select="donazioak/donazioa">
            <tr>
              <xsl:for-each select="enpresaEmaile">
                <td><xsl:value-of select="izena"/></td>
                <td><xsl:value-of select="IFK"/></td>
              </xsl:for-each>
              <td><xsl:value-of select="data"/></td>
              <xsl:for-each select="elikagaia">
                <td><xsl:value-of select="@mota"/></td>
                <td><xsl:value-of select="ref"/></td>
                <td><xsl:value-of select="prodIzena"/></td>
                <td><xsl:value-of select="kop"/></td>
                <td><xsl:value-of select="manufakEnp"/></td>
                <td><xsl:value-of select="iraun-data"/></td>
                <td><xsl:value-of select="kontserba"/></td>
                <td><xsl:value-of select="hoztu"/></td>
              </xsl:for-each>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>