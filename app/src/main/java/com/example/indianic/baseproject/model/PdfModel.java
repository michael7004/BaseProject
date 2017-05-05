package com.example.indianic.baseproject.model;

/**
 * PdfModel class created on 02/05/17.
 */

public class PdfModel {
    String name;
    String img;

    public PdfModel(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
