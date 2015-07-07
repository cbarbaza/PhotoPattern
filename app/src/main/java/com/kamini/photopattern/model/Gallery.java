package com.kamini.photopattern.model;

/**
 * Created by kamini on 12/05/15.
 */
public class Gallery {
    private int id;
    private String path;
    private float lat;
    private float lng;
    private String dadd;
    private String note;
    private String title;

    // Get Set ID
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    // Get Set PATH
    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }

    // Get Set LAT
    public float getLat(){
        return this.lat;
    }
    public void setLat(float lat){
        this.lat = lat;
    }

    // Get Set LNG
    public float getLng(){
        return this.lng;
    }
    public void setLng(float lng){
        this.lng = lng;
    }

    // Get Set DADD
    public String getDadd(){
        return dadd;
    }
    public void setDadd(String dadd){
        this.dadd = dadd;
    }
    // Get Set NOTE
    public String getNote(){
        return note;
    }
    public void setNote(String note){
        this.note = note;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Img [id=" + id + ", title=" + title + ", path=" + path + ", lng=" + lng  +", lat=" + lat + ", dadd=" + dadd + ", note=" + note + "]";
    }

    // construtor
    public Gallery(int id, String title, String path, float lat, float lng, String dadd, String note) {
        super();
        this.id = id;
        this.title = title;
        this.path = path;
        this.lat = lat;
        this.lng = lng;
        this.dadd = dadd;
        this.note = note;
    }

    public Gallery() {
        super();
    }


}