package com.company;

import java.io.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Star s1 = new Star("AAA1111", -23.0, 40, "DefaultConstellation", "PN", 5000, 30, new RightAscension(20, 50, 50), new Declination(22, 50, 40));
        Star s2 = new Star("BBB2222", -15.0, 200, "DefaultConstellation", "PD", 10000, 50, new RightAscension(15, 20, 30), new Declination(60, 70, 80));
        Star s3 = new Star("CCC3333", 5, 100, "Constellation2", "PN", 40000, 70, new RightAscension(5, 50, 15), new Declination(59, 30, 19));
        Star s4 = new Star("DDD4444", 10, 150, "Constellation3", "PD", 6000, 40, new RightAscension(12, 53, 24), new Declination(64, 21, 53));

        List<Object> listOfStars = new ArrayList<>();
        listOfStars.add(s1);
        listOfStars.add(s2);
        listOfStars.add(s3);
        listOfStars.add(s4);

        SaveToFile(listOfStars);
        List<Star> temp = new ArrayList<>();

        temp = ReadStarsFromFile();
        Star ss = null;
        for (int i = 0; i < temp.size(); i++) {
            ss = temp.get(i);
            ss.SetCatalogName();
            System.out.println(ss.ToString());
        }

    }

    public static void SaveToFile(List<Object> listOfStars){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("stars.txt"));
            for (int i = 0; i < listOfStars.size(); i++) {
                outputStream.writeObject(listOfStars.get(i));
            }
            outputStream.close();
        }catch (IOException e){
            System.out.println("IOException is caught in SaveToFile method");
        }
    }

    public static List<Star> ReadStarsFromFile(){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("stars.txt"));
            Object object = null;

                object = inputStream.readObject();
            while(object != null) {
                if (object instanceof Star) {
                    listOfStars.add((Star) object);
                }
                object = inputStream.readObject();
            }
            inputStream.close();
        }catch (EOFException e){

        }catch(IOException e) {
            System.out.println("IOException is caught in ReadStarsFromFile");
        }catch(ClassNotFoundException e){
            System.out.println("ClassNotFoundException is caught in ReadStarsFromFile");
        }
        return listOfStars;
    }


}