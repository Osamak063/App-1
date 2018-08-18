package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class ScholarModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setUrsDate(String ursDate) { this.ursDate = ursDate; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getBirthDate() { return birthDate; }
    public String getUrsDate() { return ursDate; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + birthDate + ", " + ursDate + ", " + photoUrl;
    }

    public ScholarModel(ScholarModel scholarModel) {

        super(scholarModel);
        birthDate = scholarModel.birthDate ;
        ursDate = scholarModel.ursDate;
        photoUrl = scholarModel.photoUrl;
    }

    public ScholarModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                        int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                        String birthDate, String ursDate, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.birthDate = birthDate ;
        this.ursDate = ursDate;
        this.photoUrl = photoUrl;
    }

    public ScholarModel() {

        super();
        birthDate = "" ;
        ursDate = "" ;
        photoUrl = "" ;
    }

    private String birthDate, ursDate, photoUrl;
}