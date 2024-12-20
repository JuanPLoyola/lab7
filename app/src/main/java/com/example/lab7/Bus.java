package com.example.lab7;

public class Bus {
    private String id;
    private String name;
    private String imageUrl; // Puedes almacenar URLs para imágenes en Firestore

    public Bus() {
        // Constructor vacío requerido por Firestore
    }

    public Bus(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
