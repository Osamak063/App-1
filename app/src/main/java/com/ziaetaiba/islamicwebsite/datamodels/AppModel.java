package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class AppModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public void setDownloadApp(String downloadApp) { this.downloadApp = downloadApp; }

    public String getPhotoUrl() { return photoUrl; }
    public String getDownloadApp() { return downloadApp; }

    @Override
    public String toString () {
        return super.toString() + ", " + downloadApp + ", " + photoUrl;
    }

    public AppModel(AppModel appModel) {

        super(appModel);
        downloadApp = appModel.downloadApp;
        photoUrl = appModel.photoUrl;
    }

    public AppModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                    int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                    String downloadApp, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.downloadApp = downloadApp;
        this.photoUrl = photoUrl;
    }

    public AppModel() {

        super();
        downloadApp = "" ;
        photoUrl = "" ;
    }    

    private String downloadApp, photoUrl;
}