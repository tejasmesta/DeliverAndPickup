package com.example.drivemypackage.MainActivityDealers;

public class BookingsModelClass {
    private String from;
    private String to;
    private String userId;

    public BookingsModelClass(String from, String to, String userId) {
        this.from = from;
        this.to = to;
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
