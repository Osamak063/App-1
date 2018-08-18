package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class AudioVideoHeaderModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setId(int id) { this.id = id; }
	public void setHeader(String header) {this.header = header; }

	public int getId() { return id; }
	public String getHeader() { return header; }

	@Override
	public String toString() {
		return id + ", " + header ;
	}

	public AudioVideoHeaderModel(AudioVideoHeaderModel mainItemModel) {


		id = mainItemModel.id;
		header = mainItemModel.header;
	}

	public AudioVideoHeaderModel(int id, String header) {

		this.id = id;
		this.header = header ;
	}

	public AudioVideoHeaderModel() {

		super();
		id = 0;
		header = "" ;
	}

	private int id ;
	private String header;
}