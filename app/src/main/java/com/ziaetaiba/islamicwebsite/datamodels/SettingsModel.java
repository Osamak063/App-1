package com.ziaetaiba.islamicwebsite.datamodels;

import java.io.Serializable;

public class SettingsModel implements Serializable {
   
	private static final long serialVersionUID = 1L;

    public void setId (int id) { this.id = id; }
    public void setStayAwake (String awake) { this.awake = awake; }
    public void setVersion (String version) { this.version = version ; }
    public void setDataPath(String dataPath) { this.dataPath = dataPath; }
    public void setAutoUpdate(String update) { this.update = update; }
    
    public int getId () { return id ; }
    public String getStayAwake () { return awake ; }
    public String getVersion () { return version ; }
    public String getDataPath() { return dataPath; }
    public String getAutoUpdate() { return update; }
    
    @Override
    public String toString () {
        return id + ", " + awake + ", " + version + ", " + dataPath + ", " + update;
    }
    
    public SettingsModel(SettingsModel setBean) {
        
    	id = setBean.id;
    	awake = setBean.awake ;
        version = setBean.version ;
        dataPath = setBean.dataPath;
        dataPath = setBean.dataPath ;
    }    
    
    public SettingsModel(int id, String awake, String version, String dataPath, String update) {
        
    	this.id = id ;
    	this.awake = awake ;
        this.version = version ;
        this.dataPath = dataPath;
        this.update = update;
    }    
    
    public SettingsModel() {
    
    	id = 0 ;
    	awake = "" ;
        version = "" ;
        dataPath = "" ;
        update = "" ;
    }    
    
    private int id ;
    private String awake;
    private String version ;
    private String dataPath;
    private String update;
}