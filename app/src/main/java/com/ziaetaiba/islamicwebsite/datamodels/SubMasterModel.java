package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class SubMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setNumber (int number) { this.number = number; }
    public void setMasterId (int masterId) { this.masterId = masterId; }
    public void setServiceId (int serviceId) { this.serviceId = serviceId; }
    public void setParent(String parent) { this.parent = parent; }
    public void setTitle(String title) { this.title = title; }

    public int getId () { return id ; }
    public int getNumber () { return number ; }
    public int getMasterId () { return masterId ; }
    public int getServiceId () { return serviceId ; }
    public String getParent() { return parent; }
    public String getTitle() { return title; }

    @Override
    public String toString () {
        return id + ", " + number + ", " + masterId + ", " + serviceId + ", " + parent + ", " + title ;
    }

    public SubMasterModel(SubMasterModel bookModel) {

        id = bookModel.id;
        number = bookModel.number;
        masterId = bookModel.masterId ;
        serviceId = bookModel.serviceId ;
        parent = bookModel.parent;
        title = bookModel.title;
    }

    public SubMasterModel(int id, int number, int masterId, int serviceId, String parent, String title) {

        this.id = id ;
        this.number = number ;
        this.masterId = masterId ;
        this.serviceId = serviceId ;
        this.parent = parent;
        this.title = title;
    }

    public SubMasterModel() {

        id = 0 ;
    	number = 0 ;
        masterId = 0 ;
        serviceId = 0 ;
        parent = "" ;
        title = "" ;
    }    
    
    private int id, number, masterId, serviceId;
    private String parent, title;
}