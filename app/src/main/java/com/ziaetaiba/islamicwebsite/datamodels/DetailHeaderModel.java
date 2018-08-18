package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class DetailHeaderModel implements Serializable {

	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setNo (int no) { this.no = no; }
    public void setShare(boolean share) { this.share = share; }
    public void setWeb(boolean web) { this.web = web; }
    public void setDownload(boolean download) { this.download = download; }
    public void setView(boolean view) { this.view = view; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public int getId () { return id ; }
    public int getNo () { return no ; }
    public boolean isShare() { return share; }
    public boolean isWeb() { return web; }
    public boolean isDownload() { return download; }
    public boolean isView() { return view; }
    public String getPhotoUrl() { return photoUrl; }

    @Override
    public String toString () {
        return id + ", " + no + ", " + share + ", " + web + ", " + download + ", " + view + ", " + photoUrl;
    }

    public DetailHeaderModel(DetailHeaderModel detailHeaderModel) {

    	id = detailHeaderModel.id;
        no = detailHeaderModel.no;
        share = detailHeaderModel.share ;
        web = detailHeaderModel.web ;
        download = detailHeaderModel.download;
        view = detailHeaderModel.view ;
        photoUrl = detailHeaderModel.photoUrl;
    }

    public DetailHeaderModel(int id, int no, boolean share, boolean web, boolean download, boolean view, String photoUrl) {

    	this.id = id ;
        this.no = no ;
        this.share = share ;
        this.web = web ;
        this.download = download ;
        this.view = view ;
        this.photoUrl = photoUrl;
    }

    public DetailHeaderModel() {
    
    	id = 0 ;
        no = 0 ;
        share = false ;
        web = false ;
        download = false ;
        view = false ;
        photoUrl = "" ;
    }    
    
    private int id, no ;
    private boolean share, web, download, view;
    private String photoUrl;
}