package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class AudioVideoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setMediaId(int mediaId) { this.mediaId = mediaId; }
    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setType(String type) { this.type = type; }

    public int getId () { return id ; }
    public int getMediaId() { return mediaId; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getType() { return type; }

    @Override
    public String toString () {
        return id + ", " + mediaId + ", " + title + ", " + url + ", " + type ;
    }

    public AudioVideoModel(AudioVideoModel audioVideoModel) {

        id = audioVideoModel.id;
        mediaId = audioVideoModel.mediaId;
        title = audioVideoModel.title;
        url = audioVideoModel.url ;
        type = audioVideoModel.type ;
    }

    public AudioVideoModel(int id, int mediaId, String title, String url, String type) {

        this.id = id ;
        this.mediaId = mediaId;
        this.title = title ;
        this.url = url ;
        this.type = type ;
    }

    public AudioVideoModel() {

        id = 0 ;
        mediaId = 0 ;
        title = "" ;
        url = "" ;
        type = "" ;
    }

    private int id, mediaId;
    private String title, url, type;
}