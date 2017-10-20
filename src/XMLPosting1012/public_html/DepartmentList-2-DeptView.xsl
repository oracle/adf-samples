<?xml version = '1.0' encoding = 'WINDOWS-1252'?>
<Departments xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <Department>
      <xsl:for-each select="DepartmentList/Department">
        <Deptno><xsl:value-of select="Id"/></Deptno>
        <Dname><xsl:value-of select="Name"/></Dname>
        <Loc><xsl:value-of select="Location"/></Loc>
        <Employees>
           <xsl:for-each select="Employees/Employee">
             <Employee>
                <xsl:if test="@Status='Terminated'">
                  <xsl:attribute name="bc4j-action">remove</xsl:attribute>
                </xsl:if>
                <Empno><xsl:value-of select="Id"/></Empno>
                <Ename><xsl:value-of select="Name"/></Ename>
                <xsl:if test="Salary">
                  <Sal><xsl:value-of select="Salary"/></Sal>
                </xsl:if>
             </Employee>
           </xsl:for-each>
        </Employees>
     </xsl:for-each>
   </Department>
</Departments>
