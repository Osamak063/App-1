package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class NoConnectionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setId(int id) { this.id = id; }
    public void setTitle(String title) {this.title = title; }
	public void setDetail(String detail) {this.detail = detail; }
    public void setIconId(int iconId) { this.iconId = iconId; }

	public int getId() { return id; }
    public String getTitle() { return title; }
	public String getDetail() { return detail; }
    public int getIconId() { return iconId; }

	@Override
	public String toString() {
		return id + ", " + title + ", " + detail + ", " + iconId ;
	}

	public NoConnectionModel(NoConnectionModel mainItemModel) {

		id = mainItemModel.id;
		title = mainItemModel.title;
		detail = mainItemModel.detail;
        iconId = mainItemModel.iconId ;
	}

	public NoConnectionModel(int id, String title, String detail, int iconId) {

		this.id = id;
		this.title = title;
		this.detail = detail ;
        this.iconId = iconId ;
	}

	public NoConnectionModel() {

		id = 0;
		title = "";
		detail = "" ;
		iconId = 0 ;
	}

	private int id, iconId ;
	private String title, detail;
}