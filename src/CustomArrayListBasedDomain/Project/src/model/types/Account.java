/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model.types;

import java.io.Serializable;


public class Account implements Serializable {

    private String accountid;
    private String accountname;
    
    
    public Account() {  
    }
    
    public Account(String id,String name) {
        this.accountid = id;
        this.accountname = name;
    }    

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountname() {
        return accountname;
    }


}
