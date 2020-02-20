package com.owner.my_interapec;

public class RowModel {
    private int id;
    private String name;
    private String phrase;
    private byte[] image;

    public RowModel(int id, String name, String phrase, byte[] image) {
        this.id = id;
        this.name = name;
        this.phrase = phrase;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}


