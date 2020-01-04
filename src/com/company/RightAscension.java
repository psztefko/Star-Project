package com.company;

import java.io.Serializable;

public class RightAscension implements Serializable {
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
