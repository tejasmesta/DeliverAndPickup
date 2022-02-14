package com.example.drivemypackage.MainActivityDealers;

public class DriversModelClass {
    private String userId;
    private String Name;
    private String Status;
    private String Number;
    private String fromS;
    private String fromC;
    private String toS;
    private String toC;

    public DriversModelClass(String userId, String name, String status, String number, String fromS, String fromC, String toS, String toC) {
        this.userId = userId;
        Name = name;
        Status = status;
        Number = number;
        this.fromS = fromS;
        this.fromC = fromC;
        this.toS = toS;
        this.toC = toC;
    }

    public String getFromS() {
        return fromS;
    }

    public void setFromS(String fromS) {
        this.fromS = fromS;
    }

    public String getFromC() {
        return fromC;
    }

    public void setFromC(String fromC) {
        this.fromC = fromC;
    }

    public String getToS() {
        return toS;
    }

    public void setToS(String toS) {
        this.toS = toS;
    }

    public String getToC() {
        return toC;
    }

    public void setToC(String toC) {
        this.toC = toC;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
