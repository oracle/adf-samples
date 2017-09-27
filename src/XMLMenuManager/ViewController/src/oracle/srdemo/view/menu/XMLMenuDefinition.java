/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/XMLMenuManager/ViewController/src/oracle/srdemo/view/menu/XMLMenuDefinition.java,v 1.2 2005/12/01 15:34:07 steve Exp $
package oracle.srdemo.view.menu;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import oracle.adf.view.faces.model.ChildPropertyTreeModel;
import oracle.adf.view.faces.model.MenuModel;
import oracle.adf.view.faces.model.ViewIdPropertyMenuModel;
import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
/**
 * Represents a hierarhical tree of menu items.
 * 
 * Menu item structure is read from an XML file complying with the
 * Menus.xsd XML schema for the "http://srdemo.org/menus" namespace
 * using Apache Commons Digester.
 */
public class XMLMenuDefinition {
  private Digester digester;
  Menu menu;
  MenuModel model = null;
  List<Item> items = new ArrayList<Item>();

  /**
   * Get an ADF Faces MenuModel for the menu definition.
   * @return MenuModel for the menu definition
   * @throws IntrospectionException
   */
  public MenuModel getModel() throws IntrospectionException {
    checkMenuDefinitionFileExists();
    if (model == null) {
      model = new XMLMenuTreeModel();
    }
    return model;
  }

  /**
   * Get the list of the top-level menu items.
   * @return list of the top-level menu items
   */
  public List<Item> getItems() {
    checkMenuDefinitionFileExists();
    return menu.getItems();
  }

  /**
   * Constructs a new menu definition.
   */
  public XMLMenuDefinition() {
    digester = new Digester();
    digester.setValidating(false);
    digester.addRuleSet(new MenuRuleSet());
  }

  /**
   * Set the name of the menu definition file.
   * @param menuDefinitionFile the name of the menu definition XML file
   * @throws IOException
   * @throws SAXException
   */
  public void setMenuDefinitionFile(String menuDefinitionFile) throws IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource(menuDefinitionFile);
    menu = (Menu)digester.parse(url.openStream());
  }
  private void checkMenuDefinitionFileExists() {
    if (menu == null) {
      throw new RuntimeException("The menuDefinitionFile property has not been set");
    }
  }

  /**
   * Apache Digester rule set to construct a Menu containing
   * a tree of Items from XML.
   */
  class MenuRuleSet extends RuleSetBase {
    /**
     * Adds digester rule set specific to this XML menu definition format.
     * @param d Digester instance to apply the rules to
     */
    public void addRuleInstances(Digester d) {
      d.addFactoryCreate("menu", new MenuFactoryCreate());
      d.addObjectCreate("menu/item", Item.class);
      d.addSetProperties("menu/item");
      d.addSetNext("menu/item", "addItem");
      d.addObjectCreate("*/item", Item.class);
      d.addSetProperties("*/item");
      d.addSetNext("*/item", "addItem");
    }
  }
  class XMLMenuTreeModel extends ViewIdPropertyMenuModel {
    XMLMenuTreeModel() throws IntrospectionException {
      super(new ChildPropertyTreeModel(menu.getItems(), "items"), "viewId");
    }
  }

  /**
   * Used by the MenuRuleSet to construct an instance of the private Menu class.
   */
  class MenuFactoryCreate extends AbstractObjectCreationFactory {
    /**
     * Returns a new instance of the private Menu class.
     * @param attributes XML attributes of the current element.
     * @return new instance of the private Menu class
     */
    public Object createObject(Attributes attributes) {
      return new Menu();
    }
  }
  class Menu {
    List<Item> items = new ArrayList<Item>();
    Menu() {
    }

    /**
     * Adds a top-level Item to this menu.
     * @param i Menu item to add
     */
    public void addItem(Item i) {
      items.add(i);
    }
    List<Item> getItems() {
      return items;
    }
  }
}
