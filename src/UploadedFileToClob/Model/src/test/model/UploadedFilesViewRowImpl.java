/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class UploadedFilesViewRowImpl extends ViewRowImpl {
    public static final int ID = 0;
    public static final int FILENAME = 1;
    public static final int CONTENT = 2;

    /**This is the default constructor (do not remove)
     */
    public UploadedFilesViewRowImpl() {
    }

    /**Gets UploadedFiles entity object.
     */
    public UploadedFilesImpl getUploadedFiles() {
        return (UploadedFilesImpl)getEntity(0);
    }

    /**Gets the attribute value for ID using the alias name Id
     */
    public DBSequence getId() {
        return (DBSequence)getAttributeInternal(ID);
    }

    /**Sets <code>value</code> as attribute value for ID using the alias name Id
     */
    public void setId(DBSequence value) {
        setAttributeInternal(ID, value);
    }

    /**Gets the attribute value for FILENAME using the alias name Filename
     */
    public String getFilename() {
        return (String) getAttributeInternal(FILENAME);
    }

    /**Sets <code>value</code> as attribute value for FILENAME using the alias name Filename
     */
    public void setFilename(String value) {
        setAttributeInternal(FILENAME, value);
    }


    /**getAttrInvokeAccessor: generated method. Do not modify.
     */
    protected Object getAttrInvokeAccessor(int index, 
                                           AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case ID:
            return getId();
        case FILENAME:
            return getFilename();
        case CONTENT:
            return getContent();
        default:
            return super.getAttrInvokeAccessor(index, attrDef);
        }
    }

    /**setAttrInvokeAccessor: generated method. Do not modify.
     */
    protected void setAttrInvokeAccessor(int index, Object value, 
                                         AttributeDefImpl attrDef) throws Exception {
        switch (index) {
        case ID:
            setId((DBSequence)value);
            return;
        case FILENAME:
            setFilename((String)value);
            return;
        case CONTENT:
            setContent((ClobDomain)value);
            return;
        default:
            super.setAttrInvokeAccessor(index, value, attrDef);
            return;
        }
    }

    /**Sample exportable method.
     */
    public void sampleUploadedFilesViewRowImplExportable() {
    }

    /**Sample exportable method.
     */
    public void sampleUploadedFilesViewRowImplExportable2(String testParam1) {
    }

    /**Gets the attribute value for CONTENT using the alias name Content
     */
    public ClobDomain getContent() {
        return (ClobDomain) getAttributeInternal(CONTENT);
    }

    /**Sets <code>value</code> as attribute value for CONTENT using the alias name Content
     */
    public void setContent(ClobDomain value) {
        setAttributeInternal(CONTENT, value);
    }
}
