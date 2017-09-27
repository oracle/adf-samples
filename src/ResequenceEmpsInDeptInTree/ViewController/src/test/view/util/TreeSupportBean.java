/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.util;

import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
/*
 * This backing bean can be used by any page with a tree or can be
 * used as a base class for a backing bean that's supporting a tree
 * which need additional custom code.
 */
public class TreeSupportBean {
    public RowIterator getSelectedNodeRowIterator() {
        if (getTree() != null && getTree().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getTree().getSelectedRowKeys()) {
              getTree().setRowKey(opaqueFacesKey);
              return ((JUCtrlHierNodeBinding)getTree().getRowData()).getRowIterator();
            }
        }
        return null;
    }
    public boolean isSelectedNodeInRowIteratorFirst() {
        RowIterator ri = getSelectedNodeRowIterator();
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey();
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.first().getKey());
            }
        }
        return false;
    }
    public boolean isSelectedNodeInRowIteratorLast() {
        RowIterator ri = getSelectedNodeRowIterator();
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey();
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.last().getKey());
            }
        }
        return false;
    }
    public Key getSelectedNodeRowKey() {
        if (getTree() != null && getTree().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getTree().getSelectedRowKeys()) {
              getTree().setRowKey(opaqueFacesKey);
              return ((JUCtrlHierNodeBinding)getTree().getRowData()).getRowKey();
            }
        }
        return null;
    }
    public String getSelectedNodeViewDefFullName() {
        if (getTree() != null && getTree().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getTree().getSelectedRowKeys()) {
              getTree().setRowKey(opaqueFacesKey);
              return ((JUCtrlHierNodeBinding)getTree().getRowData()).getViewObject().getDefFullName();
            }
        }
        return null;
    }    
    public void setTree(RichTree tree) {
        this.tree = tree;
    }

    public RichTree getTree() {
        return tree;
    }    
    private RichTree tree;
}
