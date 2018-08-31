package com.example.fed.calcettiamo20;

class FootballPitches {

    //campi
    private String soccerName;
    private String address;
    private String price;


    //costruttori
    public FootballPitches() {
    }

    public FootballPitches(String soccerName, String address, String price) {
        this.soccerName = soccerName;
        this.address = address;
        this.price = price;
    }


    //getters&setters
    public String getSoccerName() {
        return soccerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public void setSoccerName(String soccerName) {
        this.soccerName = soccerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
