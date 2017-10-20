<?xml version="1.0" encoding="UTF-8"?>
<DepartmentList xmlns:xsi="http://www.w3.org/2000/10/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xsi:noNamespaceSchemaLocation="DepartmentList.xsd"
                xsl:version="1.0">
  <xsl:for-each select="Departments/Department">
    <Department>
      <Id><xsl:value-of select="Deptno"/></Id>
      <Name><xsl:value-of select="Dname"/></Name>
      <Location><xsl:value-of select="Loc"/></Location>
      <Employees>
        <xsl:for-each select="Employees/Employee">
          <Employee>
            <Id><xsl:value-of select="Empno"/></Id>
            <Name><xsl:value-of select="Ename"/></Name>
            <Salary><xsl:value-of select="Sal"/></Salary>
          </Employee>
        </xsl:for-each>
      </Employees>
    </Department>
  </xsl:for-each>
</DepartmentList>
