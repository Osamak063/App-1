package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class MasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setNumber (int number) { this.number = number; }
    public void setServiceId (int serviceId) { this.serviceId = serviceId; }
    public void setParent(String parent) { this.parent = parent; }
    public void setTitle(String title) { this.title = title; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setStatus(String status) { this.status = status; }

    public int getId () { return id ; }
    public int getNumber () { return number ; }
    public int getServiceId () { return serviceId ; }
    public String getParent() { return parent; }
    public String getTitle() { return title; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getStatus() { return status; }

    @Override
    public String toString () {
        return id + ", " + number + ", " + serviceId + ", " + parent + ", " + title + ", " + thumbnailUrl + ", " + status ;
    }

    public MasterModel(MasterModel bookModel) {

        id = bookModel.id;
        number = bookModel.number;
        serviceId = bookModel.serviceId ;
        parent = bookModel.parent;
        title = bookModel.title;
        thumbnailUrl = bookModel.thumbnailUrl ;
        status = bookModel.status;
    }

    public MasterModel(int id, int number, int serviceId, String parent, String title, String thumbnailUrl, String status) {

        this.id = id ;
        this.number = number ;
        this.serviceId = serviceId ;
        this.parent = parent;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl ;
        this.status = status;
    }

    public MasterModel() {

        id = 0 ;
    	number = 0 ;
        serviceId = 0 ;
        parent = "" ;
        title = "" ;
        thumbnailUrl = "" ;
        status = "" ;
    }    
    
    private int id, number, serviceId;
    private String parent, title, thumbnailUrl, status;
}