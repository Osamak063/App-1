package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class PlaceModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setCategory(String category) { this.category = category; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCategory() { return category; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + category + ", " + photoUrl;
    }

    public PlaceModel(PlaceModel placeModel) {

        super(placeModel);
        category = placeModel.category;
        photoUrl = placeModel.photoUrl;
    }

    public PlaceModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                      int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                      String category, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.category = category ;
        this.photoUrl = photoUrl;
    }

    public PlaceModel() {

        super();
        category = "" ;
        photoUrl = "" ;
    }

    private String category, photoUrl;
}