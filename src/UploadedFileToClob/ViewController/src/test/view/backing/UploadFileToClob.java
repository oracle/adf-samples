/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.component.core.input.CoreInputFile;
import oracle.adf.view.faces.model.UploadedFile;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.BlobDomain;

import oracle.jbo.domain.ClobDomain;

import test.model.common.ExampleModule;

import test.view.util.EL;
public class UploadFileToClob {
  CoreInputFile inputFileComponent;
  private CoreInputFile fileInputComponent;
  private BindingContainer bindings;
  public UploadFileToClob() {
  }
  public void onFileUploaded(ValueChangeEvent event) {
    Reader in;
    Writer out;
    UploadedFile file = (UploadedFile)event.getNewValue();
    if (file != null && file.getLength() > 0) {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = 
        new FacesMessage("Successfully uploaded file '" + file.getFilename() + 
                         "' (" + file.getLength() + " bytes)");
      context.addMessage(event.getComponent().getClientId(context), message);
      try {
        ExampleModule exampleModule = 
          (ExampleModule)EL.get("#{bindings.UploadedFilesIterator.dataControl.dataProvider}");
        exampleModule.saveUploadedFile(newClobDomainForInputStream(file.getInputStream()), 
                                       file.getFilename());
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      } catch (SQLException s) {
        s.printStackTrace();
        throw new RuntimeException(s);
      }
    }
  }
  private ClobDomain newClobDomainForInputStream(InputStream in) throws SQLException, IOException {
    ClobDomain b = new ClobDomain();
    Writer out = b.getCharacterOutputStream();
    writeInputStreamToWriter(in,out);
    in.close();
    out.close();
    return b;
  }
  private static void writeInputStreamToWriter(InputStream in, Writer out) throws IOException {
    InputStreamReader isr = new InputStreamReader(in);
    char[] buffer = new char[8192];
    int charsRead = 0;
    while ((charsRead = isr.read(buffer, 0, 8192)) != -1) {
           out.write(buffer, 0, charsRead);
    }    
  }
  public String onUploadFileButtonClicked() {
    if (this.getFileInputComponent().getValue() == null) {
      FacesContext context = FacesContext.getCurrentInstance();
      FacesMessage message = 
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please supply a valid file name to upload", null);
      context.addMessage(this.getFileInputComponent().getId(), message);
    }
    else {
      OperationBinding ab = (OperationBinding)EL.get("#{bindings.RefreshLastFiveList}");
      ab.execute();
    }
    return null;
  }
  public void setFileInputComponent(CoreInputFile fileInputComponent) {
    this.fileInputComponent = fileInputComponent;
  }
  public CoreInputFile getFileInputComponent() {
    return fileInputComponent;
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
}
