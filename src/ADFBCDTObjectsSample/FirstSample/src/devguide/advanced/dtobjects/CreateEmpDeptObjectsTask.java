/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package devguide.advanced.dtobjects;
import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.compiler.Compiler;
import oracle.ide.log.LogManager;
import oracle.ide.model.Project;
import oracle.ide.model.Workspace;
import oracle.jbo.dt.objects.JboAppModule;
import oracle.jbo.dt.objects.JboApplication;
import oracle.jbo.dt.objects.JboAssociation;
import oracle.jbo.dt.objects.JboAssociationEnd;
import oracle.jbo.dt.objects.JboAttribute;
import oracle.jbo.dt.objects.JboAttributeList;
import oracle.jbo.dt.objects.JboBaseObject;
import oracle.jbo.dt.objects.JboEntity;
import oracle.jbo.dt.objects.JboEntityAttr;
import oracle.jbo.dt.objects.JboPackage;
import oracle.jbo.dt.objects.JboView;
import oracle.jbo.dt.objects.JboViewLink;
import oracle.jbo.dt.objects.JboViewLinkEnd;
import oracle.jbo.dt.objects.JboViewLinkUsage;
import oracle.jbo.dt.objects.JboViewReference;
import oracle.jbo.dt.ui.main.DtuUtil;
public class CreateEmpDeptObjectsTask {
  public void performTask() {
    Project activeProject = Ide.getActiveProject();
    JboApplication app = createJboApp(activeProject, "scott");
    JboPackage pkg = createJboPackage("devguide.advanced.dtobjects.model", app);
    JboEntity deptEO = createDeptEntityObject(app, pkg);
    JboEntity empEO = createEmpEntityObject(app, pkg);
    JboAssociation worksIn = 
      createOneToManyAssocBetween("WorksIn", deptEO, empEO, 
                                  deptEO.findAttributeByName("Deptno"), 
                                  empEO.findAttributeByName("Deptno"), pkg);
    JboView deptVO = 
      createDefaultViewObjectForEntity("DeptView", deptEO, app, pkg);
    JboView empVO = 
      createDefaultViewObjectForEntity("EmpView", empEO, app, pkg);
    JboAppModule am = createAppModule("HRModule", app, pkg);
    JboViewReference master = 
      addViewInstanceToAppModule(am, deptVO, "Departments");
    JboViewReference detail = 
      addViewInstanceToAppModule(am, empVO, "Employees");
    JboViewLink worksInLink = 
      createOneToManyViewLinkBetween("WorksInLink", deptVO, empVO, worksIn, 
                                     pkg);
    addViewLinkInstanceToAppModule(am, master, detail, worksInLink, "WorksIn");
    buildActiveProject();
  }
  private JboViewLink createOneToManyViewLinkBetween(String name, 
                                                     JboView source, 
                                                     JboView target, 
                                                     JboAssociation assoc, 
                                                     JboPackage pkg) {
    JboViewLink vLink = new JboViewLink();
    vLink.setName(name);
    JboViewLinkEnd sourceEnd = new JboViewLinkEnd();
    sourceEnd.setOwningView(source);
    sourceEnd.setCardinality(JboAssociationEnd.CARDINALITY_ONE);
    sourceEnd.setAttributes(matchingViewAttributes(source,assoc,true));
    JboViewLinkEnd targetEnd = new JboViewLinkEnd();
    targetEnd.setOwningView(target);
    targetEnd.setCardinality(JboAssociationEnd.CARDINALITY_MANY);
    targetEnd.setAttributes(matchingViewAttributes(target,assoc,false));
    vLink.setSrcEnd(sourceEnd);
    vLink.setDstEnd(targetEnd);
    pkg.addChild(vLink);
    save(vLink);
    save(pkg);
    return vLink;
  }
  private JboAttributeList matchingViewAttributes(JboView view,JboAssociation assoc, boolean source) {
    JboAttributeList list = new JboAttributeList();
    JboAttributeList endList = null;
    if (source) {
      endList = assoc.getSrcEnd().getAttributes();
    }
    else {
      endList = assoc.getDstEnd().getAttributes();
    }
    for (JboAttribute attr : endList.getAttributes()) {
      list.add(view.findMatchingAttr((JboEntityAttr)attr));
    }
    return list;
  }
  private JboAssociation createOneToManyAssocBetween(String name, 
                                                     JboEntity source, 
                                                     JboEntity target, 
                                                     JboAttribute sourceAttr, 
                                                     JboAttribute targetAttr, 
                                                     JboPackage pkg) {
    JboAssociation assoc = new JboAssociation();
    assoc.setName(name);
    JboAssociationEnd sourceEnd = new JboAssociationEnd();
    JboAttributeList sourceAttrList = new JboAttributeList();
    sourceAttrList.add(sourceAttr);
    sourceEnd.setOwningEntity(source);
    sourceEnd.setAttributes(sourceAttrList);
    sourceEnd.setCardinality(JboAssociationEnd.CARDINALITY_ONE);
    JboAssociationEnd targetEnd = new JboAssociationEnd();
    targetEnd.setOwningEntity(target);
    JboAttributeList targetAttrList = new JboAttributeList();
    targetAttrList.add(targetAttr);
    targetEnd.setOwningEntity(target);
    targetEnd.setAttributes(targetAttrList);
    targetEnd.setCardinality(JboAssociationEnd.CARDINALITY_MANY);
    assoc.setSrcEnd(sourceEnd);
    assoc.setDstEnd(targetEnd);
    pkg.addChild(assoc);
    save(assoc);
    save(pkg);
    return assoc;
  }
  private JboViewReference addViewInstanceToAppModule(JboAppModule am, 
                                                      JboView vo, 
                                                      String instanceName) {
    JboViewReference voi = new JboViewReference();
    voi.setName(instanceName);
    voi.setView(vo);
    am.addViewReference(voi);
    save(am);
    return voi;
  }
  private JboViewLinkUsage addViewLinkInstanceToAppModule(JboAppModule am, 
                                                          JboViewReference master, 
                                                          JboViewReference detail, 
                                                          JboViewLink viewLink, 
                                                          String instanceName) {
    JboViewLinkUsage viewLinkUsage = new JboViewLinkUsage();
    viewLinkUsage.setName(instanceName);
    viewLinkUsage.setSrcViewRef(master);
    viewLinkUsage.setDstViewRef(detail);
    viewLinkUsage.setViewLink(viewLink, false);
    am.addViewLink(viewLinkUsage);
    save(am);
    return viewLinkUsage;
  }
  private JboAppModule createAppModule(String name, JboApplication app, 
                                       JboPackage pkg) {
    JboAppModule am = new JboAppModule();
    am.setName(name);
    pkg.addChild(am);
    save(am);
    save(pkg);
    return am;
  }
  private JboEntity createEmpEntityObject(JboApplication app, JboPackage pkg) {
    JboEntity emp = new JboEntity();
    emp.setName("Emp");
    emp.setDBObjectName("EMP");
    emp.setDBObjectType("table");
    emp.addAttribute(createEmpEmpnoAttribute());
    emp.addAttribute(createEmpEnameAttribute());
    emp.addAttribute(createEmpDeptnoAttribute());
    pkg.addChild(emp);
    save(emp);
    save(pkg);
    return emp;
  }
  private JboAttribute createEmpEmpnoAttribute() {
    JboEntityAttr empno = new JboEntityAttr();
    empno.setName("Empno");
    empno.setColumnName("EMPNO");
    empno.setPrimaryKey(true);
    empno.setType("oracle.jbo.domain.Number");
    empno.setColumnType("NUMBER");
    empno.setPrecision(8);
    empno.setScale(0);
    empno.setPersistent(true);
    return empno;
  }
  private JboAttribute createEmpEnameAttribute() {
    JboEntityAttr dname = new JboEntityAttr();
    dname.setName("Ename");
    dname.setColumnName("ENAME");
    dname.setType("java.lang.String");
    dname.setColumnType("VARCHAR2");
    dname.setPrecision(10);
    dname.setPersistent(true);
    return dname;
  }
  private JboAttribute createEmpDeptnoAttribute() {
    JboEntityAttr empno = new JboEntityAttr();
    empno.setName("Deptno");
    empno.setColumnName("DEPTNO");
    empno.setType("oracle.jbo.domain.Number");
    empno.setColumnType("NUMBER");
    empno.setPrecision(8);
    empno.setScale(0);
    empno.setPersistent(true);
    return empno;
  }
  private JboEntity createDeptEntityObject(JboApplication app, 
                                           JboPackage pkg) {
    JboEntity dept = new JboEntity();
    dept.setName("Dept");
    dept.setDBObjectName("DEPT");
    dept.setDBObjectType("table");
    dept.addAttribute(createDeptDeptnoAttribute());
    dept.addAttribute(createDeptDnameAttribute());
    pkg.addChild(dept);
    save(dept);
    save(pkg);
    return dept;
  }
  private JboAttribute createDeptDeptnoAttribute() {
    JboEntityAttr deptno = new JboEntityAttr();
    deptno.setName("Deptno");
    deptno.setColumnName("DEPTNO");
    deptno.setPrimaryKey(true);
    deptno.setType("oracle.jbo.domain.Number");
    deptno.setColumnType("NUMBER");
    deptno.setPrecision(8);
    deptno.setScale(0);
    deptno.setPersistent(true);
    return deptno;
  }
  private JboAttribute createDeptDnameAttribute() {
    JboEntityAttr dname = new JboEntityAttr();
    dname.setName("Dname");
    dname.setColumnName("DNAME");
    dname.setType("java.lang.String");
    dname.setColumnType("VARCHAR2");
    dname.setPrecision(10);
    dname.setPersistent(true);
    return dname;
  }
  //  public JboEntity createEntityObjectForTable(String tableName, 
  //                                              JboApplication app, 
  //                                              JboPackage pkg) {
  //    JboEntity entity;
  //    try {
  //      entity = JboEntity.createFromDBObject(app, "DEPT", null, true);
  //    } catch (Exception e) {
  //      e.printStackTrace();
  //      return null;
  //    }
  //    pkg.addChild(entity);
  //    //  DtuUtil.saveObject(null, entity, false, false, false, false);
  //    //  DtuUtil.saveObject(null, pkg, false, false, false, false);
  //    return entity;
  //  }
  public JboApplication createJboApp(Project project, String connectionName) {
    JboApplication app = new JboApplication(project);
    app.getConnection().setName(connectionName);
    app.getConnection().setNamedConnection(connectionName);
    app.setLoaded(true);
    save(app);
    return app;
  }
  private static final void logMessage(String msg) {
    LogManager.getLogManager().showLog();
    LogManager.getLogManager().getMsgPage().log(msg + "
");
  }
  private JboPackage createJboPackage(String packageName, JboApplication app) {
    JboPackage pkg = new JboPackage();
    pkg.setName(packageName);
    pkg.setPackageName(packageName);
    app.addChild(pkg);
    save(pkg);
    save(app);
    return pkg;
  }
  private JboView createDefaultViewObjectForEntity(String viewName, 
                                                   JboEntity entity, 
                                                   JboApplication app, 
                                                   JboPackage pkg) {
    JboView view;
    try {
      view = JboView.createDefaultView(pkg, entity, viewName);
      save(pkg);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return view;
  }
  private void buildActiveProject() {
    Project activeProject = Ide.getActiveProject();
    Workspace activeWorkspace = Ide.getActiveWorkspace();
    Context c = new Context(activeWorkspace, activeProject);
    Compiler.getCompiler().compile(c, true, true, true);
  }
  private void save(JboBaseObject obj) {
    DtuUtil.saveObject(null, obj, false, false, false, false);
  }
}
