/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import oracle.jbo.Key;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class UploadedFilesImpl extends EntityImpl {
    public static final int ID = 0;
    public static final int FILENAME = 1;
    public static final int CONTENT = 2;
    public static final int DATECREATED = 3;
    private static EntityDefImpl mDefinitionObject;

  /**This is the default constructor (do not remove)
   */
  public UploadedFilesImpl() {
  }


    /**Retrieves the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("test.model.UploadedFiles");
        }
        return mDefinitionObject;
    }

    /**Gets the attribute value for Id, using the alias name Id
     */
    public DBSequence getId() {
    return (DBSequence)getAttributeInternal(ID);
  }

  /**Sets <code>value</code> as the attribute value for Id
   */
  public void setId(DBSequence value) {
    setAttributeInternal(ID, value);
  }

  /**Gets the attribute value for Filename, using the alias name Filename
   */
  public String getFilename() {
    return (String)getAttributeInternal(FILENAME);
  }

  /**Sets <code>value</code> as the attribute value for Filename
   */
  public void setFilename(String value) {
    setAttributeInternal(FILENAME, value);
  }

  /**Gets the attribute value for Content, using the alias name Content
     */
    public ClobDomain getContent() {
        return (ClobDomain)getAttributeInternal(CONTENT);
    }

  /**Sets <code>value</code> as the attribute value for Content
   */
  public void setContent(ClobDomain value) {
    setAttributeInternal(CONTENT, value);
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
        case DATECREATED:
            return getDateCreated();
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

  /**Gets the attribute value for DateCreated, using the alias name DateCreated
   */
  public Date getDateCreated() {
    return (Date)getAttributeInternal(DATECREATED);
  }

  /**Sets <code>value</code> as the attribute value for DateCreated
   */
  public void setDateCreated(Date value) {
    setAttributeInternal(DATECREATED, value);
  }

    /**Creates a Key object based on given key constituents
     */
    public static Key createPrimaryKey(DBSequence id) {
        return new Key(new Object[]{id});
    }
}
