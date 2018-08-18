package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class ProductItemModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setNo (int no) { this.no = no; }
    public void setTitle(String title) { this.title = title; }
    public void setExtension(String extension) { this.extension = extension; }
    public void setDescription(String description) { this.description = description; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public void setWebsite(String website) { this.website = website; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public int getId () { return id ; }
    public int getNo () { return no ; }
    public String getTitle() { return title; }
    public String getExtension() { return extension; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getWebsite() { return website; }
    public String getDownloadUrl() { return downloadUrl; }

    @Override
    public String toString () {
        return id + ", " + no + ", " + title + ", " + extension + ", " + description + ", "
                + thumbnailUrl + ", " + website + ", " + downloadUrl;
    }

    public ProductItemModel(ProductItemModel productItemModel) {

        id = productItemModel.id;
        no = productItemModel.no ;
        title = productItemModel.title;
        extension = productItemModel.extension ;
        description = productItemModel.description ;
        thumbnailUrl = productItemModel.thumbnailUrl ;
        website = productItemModel.website;
        downloadUrl = productItemModel.downloadUrl;
    }

    public ProductItemModel(int id, int no, String title, String extension, String description, String thumbnailUrl,
                            String website, String downloadUrl) {

        this.id = id ;
        this.no = no ;
        this.title = title;
        this.extension = extension ;
        this.description = description ;
        this.thumbnailUrl = thumbnailUrl ;
        this.website = website;
        this.downloadUrl = downloadUrl ;
    }

    public ProductItemModel() {

        id = 0 ;
        no = 0 ;
        title = "" ;
        extension = "" ;
        description = "" ;
        thumbnailUrl = "" ;
        website = "" ;
        downloadUrl = "" ;
    }    
    
    private int id, no;
    private String title, extension, description, thumbnailUrl, website, downloadUrl;
}