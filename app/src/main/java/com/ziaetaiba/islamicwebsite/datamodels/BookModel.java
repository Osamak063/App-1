package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class BookModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setCategory(String category) { this.category = category; }
    public void setAuthor(String author) { this.author = author; }
    public void setLanguage(String language) { this.language = language; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setMonth(String month) { this.month = month; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getCategory() { return category; }
    public String getAuthor() { return author; }
    public String getLanguage() { return language; }
    public String getPublisher() { return publisher; }
    public String getMonth() { return month; }
    public String getPdfUrl() { return pdfUrl; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return super.toString() + ", " + category + ", " + author + ", " + language + ", " + publisher + ", "
                + month + ", " + pdfUrl + ", " + photoUrl;
    }

    public BookModel(BookModel bookModel) {

        super(bookModel);
        category = bookModel.category;
        author = bookModel.author;
        language = bookModel.language;
        publisher = bookModel.publisher;
        month = bookModel.month;
        pdfUrl = bookModel.pdfUrl ;
        photoUrl = bookModel.photoUrl;
    }    
    
    public BookModel(int id, int no, int serviceId, String title, String description, String thumbnailUrl,
                     int featured, int mostView, int mostDownload, String website, String updatedOn, String status,
                     String category, String author, String language,
                     String publisher, String month, String pdfUrl, String photoUrl) {

        super(id, no, serviceId, title, description, thumbnailUrl, featured, mostView, mostDownload, website, updatedOn, status);
        this.category = category ;
        this.author = author ;
        this.language = language ;
        this.publisher = publisher ;
        this.month = month ;
        this.pdfUrl = pdfUrl ;
        this.photoUrl = photoUrl;
    }    
    
    public BookModel() {

        super();
        category = "" ;
        author = "" ;
        language = "" ;
        publisher = "" ;
        month = "" ;
        pdfUrl = "" ;
        photoUrl = "" ;
    }    

    private String category, author, language, publisher, month, pdfUrl, photoUrl;
}