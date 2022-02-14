package com.example.drivemypackage.MainActivityDrivers;

public class BookedByModelClass {
    private String userId;
    private String From;
    private String To;

    public BookedByModelClass(String userId, String from, String to) {
        this.userId = userId;
        From = from;
        To = to;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }
}
