package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class MediaModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setCategory(String category) { this.category = category; }
    public void setVocalist(String vocalist) { this.vocalist = vocalist; }
    public void setAttribute(String attribute) { this.attribute = attribute; }
    public void setLanguage(String language) { this.language = language; }
    public void setType(String type) { this.type = type; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCategory() { return category; }
    public String getVocalist() { return vocalist; }
    public String getAttribute() { return attribute; }
    public String getLanguage() { return language; }
    public String getType() { return type; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + category + ", " + vocalist + ", " + attribute + ", " + language + ", " +
                type + ", " + photoUrl;
    }

    public MediaModel(MediaModel mediaModel) {

        super(mediaModel);
        category = mediaModel.category;
        vocalist = mediaModel.vocalist ;
        attribute = mediaModel.attribute ;
        language = mediaModel.language ;
        type = mediaModel.type ;
        photoUrl = mediaModel.photoUrl;
    }

    public MediaModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                      int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                      String category, String vocalist, String attribute, String language, String type, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.category = category ;
        this.vocalist = vocalist ;
        this.attribute = attribute ;
        this.language = language ;
        this.type = type ;
        this.photoUrl = photoUrl;
    }

    public MediaModel() {

        super();
        category = "" ;
        vocalist = "" ;
        attribute = "" ;
        language = "" ;
        type = "" ;
        photoUrl = "" ;
    }

    private String category, vocalist, attribute, language, type, photoUrl;
}