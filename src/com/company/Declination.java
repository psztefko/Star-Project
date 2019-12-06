package com.company;

public class Declination {
    private int degrees;
    private int minutes;
    private int seconds;

    public Declination(int degrees, int minutes, int seconds) {
        if(degrees < 0 || degrees > 90){
            throw new IllegalArgumentException("Incorrect declination degrees value");
        }else this.degrees = degrees;

        if(minutes < 0 || minutes > 90){
            throw new IllegalArgumentException("Incorrect declination minutes value");
        }else this.minutes = minutes;

        if(seconds < 0 || seconds > 90){
            throw new IllegalArgumentException("Incorrect declination seconds value");
        }else this.seconds = seconds;
    }
}
