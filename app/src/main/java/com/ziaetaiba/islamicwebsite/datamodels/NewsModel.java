package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class NewsModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setOrganization(String organization) { this.organization = organization; }
    public void setCountry(String country) { this.country = country; }
    public void setNewsPaper(String newsPaper) { this.newsPaper = newsPaper; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getOrganization() { return organization; }
    public String getCountry() { return country; }
    public String getNewsPaper() { return newsPaper; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + organization + ", " + country + ", " + newsPaper + ", " + photoUrl;
    }

    public NewsModel(NewsModel newsModel) {

        super(newsModel);
        organization = newsModel.organization;
        country = newsModel.country;
        newsPaper = newsModel.newsPaper ;
        photoUrl = newsModel.photoUrl;
    }

    public NewsModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                     int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                     String organization, String country, String newsPaper, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.organization = organization ;
        this.country = country ;
        this.newsPaper = newsPaper ;
        this.photoUrl = photoUrl;
    }

    public NewsModel() {

        super();
        organization = "" ;
        country = "" ;
        newsPaper = "" ;
        photoUrl = "" ;
    }    

    private String organization, country, newsPaper, photoUrl;
}