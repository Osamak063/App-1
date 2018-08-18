package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class DayModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + photoUrl;
    }

    public DayModel(DayModel dayModel) {

        super(dayModel);
        photoUrl = dayModel.photoUrl;
    }

    public DayModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                    int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                    String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.photoUrl = photoUrl;
    }

    public DayModel() {

        super();
        photoUrl = "" ;
    }

    private String photoUrl;
}