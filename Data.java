package com.example.aashna.mynotes;

public class Data {
  private   String description;
  private   String url;
   private String type;


    public Data(String description, String url, String type) {
        this.description = description;
        this.url = url;
        this.type = type;
    }

    public  Data(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





}
