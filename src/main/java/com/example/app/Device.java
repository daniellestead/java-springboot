package com.example.app;

public class Device {
    private String id;
    private String name;
    private String status;
    private String lastUpdated;

    public Device() {
    }

    public Device(String id, String name, String status, String lastUpdated) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.lastUpdated = lastUpdated;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
