package com.company;

public class RightAscension {
    private int hours;
    private int minutes;
    private int seconds;

    public RightAscension(int hours, int minutes, int seconds) {
        if(hours < 0 || hours > 24){
            throw new IllegalArgumentException("Incorrect right ascension hours value");
        }else this.hours = hours;

        if(minutes < 0 || minutes > 60){
            throw new IllegalArgumentException("Incorrect right ascension minutes value");
        }else this.minutes = minutes;

        if(seconds < 0 || seconds > 60){
            throw new IllegalArgumentException("Incorrect right ascension seconds value");
        }else this.seconds = seconds;
    }
}
