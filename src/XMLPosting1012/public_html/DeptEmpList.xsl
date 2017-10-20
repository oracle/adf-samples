<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <head>
    <style>
      <xsl:comment>
        TABLE { background-color:#cccc99 }
      	TH { background-color:#cccc99;
              			    color:#336699;
        		       font-family: Arial;
        		       font-weight:bold;
              			 font-size: 80%; }
      	TD { background-color:#f7f7e7;
		              	    color:#000000;
        		      font-family: Arial;
              			font-size: 80% }
      </xsl:comment>
    </style>
  </head>
  <body>
    <table width="100%">
        <tr>
          <th align="left">Department</th>
          <th align="left">Employees</th>
        </tr>
      <xsl:for-each select="Departments/Department">
        <tr>
          <td nowrap="">
            <a target="otherwindow" href="DeptEmpListSchema.xsql?d={Deptno}">
              <xsl:value-of select="Dname"/>
            </a>
              <xsl:text>&#160;(</xsl:text>
              <xsl:value-of select="Deptno"/>
              <xsl:text>)</xsl:text>
          </td>
          <td>
            <xsl:for-each select="Employees/Employee">
              <xsl:value-of select="Ename"/>
              <xsl:text>&#160;(</xsl:text>
              <xsl:value-of select="Empno"/>
              <xsl:text>,$</xsl:text>
              <xsl:value-of select="Sal"/>
              <xsl:text>)</xsl:text>
              <xsl:if test="position() != last()">
                <xsl:text>, </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </td>
        </tr>
      </xsl:for-each>
    </table>
    <center><a target="otherwindow" href="DeptEmpListSchema.xsql">See the Raw XML</a></center>
  </body>
</html>

 