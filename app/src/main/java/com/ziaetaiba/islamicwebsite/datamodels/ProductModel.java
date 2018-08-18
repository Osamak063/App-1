package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class ProductModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setServiceNo (int serviceNo) { this.serviceNo = serviceNo; }
    public void setTitle(String title) { this.title = title; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public int getId () { return id ; }
    public int getServiceNo () { return serviceNo ; }
    public String getTitle() { return title; }
    public String getShortName() { return shortName; }

    @Override
    public String toString () {
        return id + ", " + serviceNo + ", " + title + ", " + shortName ;
    }

    public ProductModel(ProductModel productModel) {

        id = productModel.id;
        serviceNo = productModel.serviceNo;
        title = productModel.title;
        shortName = productModel.shortName;
    }

    public ProductModel(int id, int serviceNo, String title, String shortName) {

        this.id = id ;
        this.serviceNo = serviceNo ;
        this.title = title;
        this.shortName = shortName ;
    }

    public ProductModel() {

        id = 0 ;
    	serviceNo = 0 ;
        title = "" ;
        shortName = "" ;
    }    
    
    private int id, serviceNo ;
    private String title, shortName;
}