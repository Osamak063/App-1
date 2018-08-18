package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class GalleryModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setCategory(String category) { this.category = category; }
    public void setDownloadImage(String downloadImage) { this.downloadImage = downloadImage; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCategory() { return category; }
    public String getDownloadImage() { return downloadImage; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + category + ", " + downloadImage + ", " + photoUrl ;
    }

    public GalleryModel(GalleryModel galleryModel) {

        super(galleryModel);
        category = galleryModel.category;
        downloadImage = galleryModel.downloadImage;
        photoUrl = galleryModel.photoUrl;
    }

    public GalleryModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                        int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                        String category, String downloadImage, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.category = category ;
        this.downloadImage = downloadImage ;
        this.photoUrl = photoUrl;
    }

    public GalleryModel() {

        super();
        category = "" ;
        downloadImage = "" ;
        photoUrl = "" ;
    }    

    private String category, downloadImage, photoUrl;
}