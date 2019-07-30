package com.gmail.aprizalabyan.projectakhir;

public class Kontak {
    private int id;
    private String nama;
    private String email;
    private byte[] image;

    public Kontak(String nama, String email, byte[] image, int id) {
        this.nama = nama;
        this.email = email;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setPrice(String price) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
