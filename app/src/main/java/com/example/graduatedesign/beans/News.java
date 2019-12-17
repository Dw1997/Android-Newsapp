package com.example.graduatedesign.beans;

public class News {
    private String id;
    private String url;
    private String date;
    private String impa;
    private String title;
    private String state;
    private String type;

    public News(){

    }

    public News(String id, String url, String date, String impa, String title, String state, String type) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.impa = impa;
        this.title = title;
        this.state = state;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImpa() {
        return impa;
    }

    public void setImpa(String impa) {
        this.impa = impa;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", impa='" + impa + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
