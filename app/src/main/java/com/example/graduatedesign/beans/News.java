package com.example.graduatedesign.beans;

public class News {
    private String id;
    private String url;
    private String date;
    private String year;
    private String month;
    private String day;
    private String impa;
    private String title;
    private String state;

    public News(){

    }

    public News(String id, String url, String date, String year, String month, String day, String impa, String title, String state) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.year = year;
        this.month = month;
        this.day = day;
        this.impa = impa;
        this.title = title;
        this.state = state;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", impa='" + impa + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
