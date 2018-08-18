package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class BlogModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

     public void setCategory(String category) { this.category = category; }
    public void setWriter(String writer) { this.writer = writer; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCategory() { return category; }
    public String getWriter() { return writer; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + category + ", " + writer + ", " + photoUrl;
    }

    public BlogModel(BlogModel bookModel) {

        super(bookModel);
        category = bookModel.category;
        writer = bookModel.writer;
        photoUrl = bookModel.photoUrl;
    }

    public BlogModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                     int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                     String category, String writer, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.category = category ;
        this.writer = writer;
        this.photoUrl = photoUrl;
    }

    public BlogModel() {

        super();
        category = "" ;
        writer = "" ;
        photoUrl = "" ;
    }    

    private String category, writer, photoUrl;
}