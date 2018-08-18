package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class ServiceModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId(int id) { this.id = id; }
    public void setNumber(int number) { this.number = number; }
    public void setLanguageId(int languageId) { this.languageId = languageId; }
    public void setTitle (String title) { this.title = title; }
    public void setThumbnailUrl (String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setStatus (String status) { this.status = status; }

    public int getId() { return id; }
    public int getNumber() { return number; }
    public int getLanguageId() { return languageId; }
    public String getTitle () { return title; }
    public String getThumbnailUrl () { return thumbnailUrl; }
    public String getStatus () { return status; }

    @Override
	public String toString() {
		return id + ", " + number + ", " + languageId + ", " + title + ", " + thumbnailUrl + ", " + status ;
	}

	public ServiceModel(ServiceModel serviceModel) {

		id = serviceModel.id;
		number = serviceModel.number;
        languageId = serviceModel.languageId;
        title = serviceModel.title;
        thumbnailUrl = serviceModel.thumbnailUrl;
        status = serviceModel.status ;
    }

	public ServiceModel(int id, int number, int languageId, String title, String thumbnailUrl, String status) {

		this.id = id;
		this.number = number;
        this.languageId = languageId ;
        this.title = title;
		this.thumbnailUrl = thumbnailUrl ;
        this.status = status ;
    }

	public ServiceModel() {

		id = 0;
		number = 0;
        languageId = 0 ;
        title = "" ;
		thumbnailUrl = "" ;
        status = "" ;
    }

	private int id, number, languageId;
    private String title, thumbnailUrl, status;
}