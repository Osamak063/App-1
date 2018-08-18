package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class MainItemModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setId(int id) { this.id = id; }
	public void setHeader(String header) {this.header = header; }
    public void setTitle(String title) {this.title = title; }
    public void setIconId(int iconId) { this.iconId = iconId; }
	
	public int getId() { return id; }
	public String getHeader() { return header; }
    public String getTitle() { return title; }
    public int getIconId() { return iconId; }

	@Override
	public String toString() {
		return id + ", " + header + ", " + title + ", " + iconId ;
	}

	public MainItemModel(MainItemModel mainItemModel) {

		id = mainItemModel.id;
		header = mainItemModel.header;
        title = mainItemModel.title;
        iconId = mainItemModel.iconId ;
	}

	public MainItemModel(int id, String header, String title, int iconId) {

		this.id = id;
		this.header = header ;
		this.title = title;
        this.iconId = iconId ;
	}

	public MainItemModel() {

		id = 0;
		header = "" ;
		title = "";
		iconId = 0 ;
	}

	private int id, iconId ;
	private String header, title;
}