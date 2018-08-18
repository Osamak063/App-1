package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class LanguageModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setId (int id) { this.id = id; }
    public void setNo (int no) { this.no = no; }
    public void setTitle(String title) { this.title = title; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public int getId () { return id ; }
    public int getNo () { return no ; }
    public String getTitle() { return title; }
    public String getShortName() { return shortName; }

    @Override
    public String toString () {
        return id + ", " + no + ", " + title + ", " + shortName ;
    }

    public LanguageModel(LanguageModel titleModel) {

    	id = titleModel.id;
        no = titleModel.no ;
        title = titleModel.title ;
        shortName = titleModel.shortName ;
    }

    public LanguageModel(int id, int no, String title, String shortName) {

    	this.id = id ;
        this.no = no ;
        this.title = title;
        this.shortName = shortName ;
    }

    public LanguageModel() {
    
    	id = 0 ;
        no = 0 ;
        title = "" ;
        shortName = "" ;
    }    
    
    private int id, no ;
    private String title, shortName;
}