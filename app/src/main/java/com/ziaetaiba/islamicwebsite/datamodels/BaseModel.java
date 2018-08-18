package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setNo (int no) { this.no = no; }
    public void setServiceId (int serviceId) { this.serviceId = serviceId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setFeatured(int featured) { this.featured = featured; }
    public void setMostView(int mostView) { this.mostView = mostView; }
    public void setMostDownload(int mostDownload) { this.mostDownload = mostDownload; }
    public void setWebsite(String website) { this.website = website; }
    public void setUpdatedOn(String updatedOn) { this.updatedOn = updatedOn; }
    public void setStatus(String status) { this.status = status; }

    public int getId () { return id ; }
    public int getNo () { return no ; }
    public int getServiceId () { return serviceId ; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public int getFeatured() { return featured; }
    public int getMostView() { return mostView; }
    public int getMostDownload() { return mostDownload; }
    public String getWebsite() { return website; }
    public String getUpdatedOn() { return updatedOn; }
    public String getStatus() { return status; }

    @Override
    public String toString () {
        return id + ", " + no + ", " + serviceId + ", " + title + ", " + description + ", " + thumbnailUrl
                + ", " + featured + ", " + mostView + ", " + mostDownload + ", " + website + ", " + updatedOn + ", " + status ;
    }

    public BaseModel(BaseModel baseModel) {

        id = baseModel.id;
        no = baseModel.no;
        serviceId = baseModel.serviceId ;
        title = baseModel.title;
        description = baseModel.description ;
        thumbnailUrl = baseModel.thumbnailUrl ;
        featured = baseModel.featured;
        mostView = baseModel.mostView;
        mostDownload = baseModel.mostDownload ;
        website = baseModel.website ;
        updatedOn = baseModel.updatedOn;
        status = baseModel.status ;
    }

    public BaseModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                     int featured, int mostView, int mostDownload, String website, String updatedOn, String status) {

        this.id = id ;
        this.no = no ;
        this.serviceId = serviceId ;
        this.title = title ;
        this.description = description ;
        this.thumbnailUrl = thumbnailUrl ;
        this.featured = featured;
        this.mostView = mostView;
        this.mostDownload = mostDownload ;
        this.website = website ;
        this.updatedOn = updatedOn;
        this.status = status ;
    }

    public BaseModel() {

        id = 0 ;
    	no = 0 ;
        serviceId = 0 ;
        title = "" ;
        description = "" ;
        thumbnailUrl = "" ;
        featured = 0 ;
        mostView = 0 ;
        mostDownload = 0 ;
        website = "" ;
        updatedOn = "" ;
        status = "" ;
    }    
    
    private int id, no, serviceId, featured, mostView, mostDownload ;
    private String title, description, thumbnailUrl, website, updatedOn, status;
}