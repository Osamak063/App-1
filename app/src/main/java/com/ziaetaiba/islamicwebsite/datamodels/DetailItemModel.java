package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class DetailItemModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setId (int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDetail(String detail) { this.detail = detail; }

    public int getId () { return id ; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }

    @Override
    public String toString () {
        return id + ", " + title + ", " + detail;
    }

    public DetailItemModel(DetailItemModel detailItemModel) {

    	id = detailItemModel.id;
        title = detailItemModel.title ;
        detail = detailItemModel.detail ;
    }

    public DetailItemModel(int id, String title, String detail) {

    	this.id = id ;
        this.title = title;
        this.detail = detail ;
    }

    public DetailItemModel() {
    
    	id = 0 ;
        title = "" ;
        detail = "" ;
    }    
    
    private int id;
    private String title, detail ;
}