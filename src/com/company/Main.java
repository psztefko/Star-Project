package com.company;

import java.io.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    protected static String[] greekAlphabet = new String[]{"ALPHA", "BETA", "GAMMA", "DELTA", "EPSILON", "ZETA", "ETA", "THETA", "IOTA", "KAPPA", "LAMBDA", "MU", "NU", "XI", "OMICRON", "PI", "RHO", "SIGMA", "TAU", "UPSILON", "PHI", "CHI", "PSI", "OMEGA"};


    public static void main(String[] args) {

        Star s1 = new Star("AAA1111", -22.0, 40, "DefaultConstellation", "PN", 5000, 30, new RightAscension(20, 50, 50), new Declination(22, 50, 40));
        Star s2 = new Star("BBB2222", -15.0, 200, "DefaultConstellation", "PD", 10000, 50, new RightAscension(15, 20, 30), new Declination(60, 70, 80));
        Star s3 = new Star("CCC3333", 5, 100, "Constellation2", "PN", 40000, 5, new RightAscension(5, 50, 15), new Declination(59, 30, 19));
        Star s4 = new Star("DDD4444", 10, 150, "Constellation3", "PD", 6000, 1, new RightAscension(12, 53, 24), new Declination(64, 21, 53));

        List<Star> listOfStars = new ArrayList<>();
        listOfStars.add(s1);
        listOfStars.add(s2);
        listOfStars.add(s3);
        listOfStars.add(s4);

        SaveToFile(listOfStars, "stars.txt");

        //read all stars from file
        List<Star> temp = ReadStarsFromFile();

        //search for all stars that are between x and y parsec away from earth
        //List<Star> temp = SearchByDistance(80.0, 160.0);

        //search for all stars that have temperature between x and y
        //List<Star> temp = SearchByTemperature(5000.0, 20000.0);

        //search for all stars that have magnitudo between x and y
        //List<Star> temp = SearchByAbsoluteStarSize(10.0, 2000.0);

        //search for all stars that have given hemisphere
        //List<Star> temp = SearchByHemisphere("PN");

        //search for all stars that might be supernova's
        //List<Star> temp = SearchPotentialSupernova();

        Star ss = null;
        for (int i = 0; i < temp.size(); i++) {
            ss = temp.get(i);
            ss.SetCatalogName();
            System.out.println(ss.ToString());
        }



        String[] tempCatalogName = new String[]{"DefaultConstellation", "ALPHA"};
        RemoveObjectFromFile(tempCatalogName);

        List<Star> temp1 = ReadStarsFromFile();
        ss = null;
        for (int i = 0; i < temp1.size(); i++) {
            ss = temp1.get(i);
            ss.SetCatalogName();
            System.out.println(ss.ToString());
        }



    }

    public static void SaveToFile(List<Star> listOfStars, String fileName){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
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


    public static void RemoveObjectFromFile(String[] catalogName){
        List<Star> listOfStars = new ArrayList<>();
        int removedPositionInConstellation = 0;
        String removedStarConstellation = "";
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (Arrays.equals(catalogName, temporaryStar.getCatalogName())) {

                    }else{
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }


        Star temporaryStar = null;
        List<Star> listOfUpdatedStars = new ArrayList<>();
        for (int i = 0; i < listOfStars.size(); i++) {
            temporaryStar = listOfStars.get(i);
            Star updatedStar = new Star(temporaryStar.getName(), temporaryStar.getObservedStarSize(), temporaryStar.getDistanceInLightYears(), temporaryStar.getConstellation(), temporaryStar.getHemisphere(), temporaryStar.getTemperature(), temporaryStar.getMass(), temporaryStar.getRightAscension(), temporaryStar.getDeclination());
            listOfUpdatedStars.add(updatedStar);
        }
        SaveToFile(listOfUpdatedStars, "stars.txt");
    }

    public static List<Star> SearchByTemperature(double lowerLimit, double upperLimit){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (lowerLimit < temporaryStar.temperature && temporaryStar.temperature < upperLimit) {
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
            //e.printStackTrace();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        return listOfStars;
    }

    public static List<Star> SearchByDistance(double lowerLimit, double upperLimit){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (lowerLimit*3.26 < temporaryStar.distanceInLightYears && temporaryStar.distanceInLightYears < upperLimit*3.26) {
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
            //e.printStackTrace();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        return listOfStars;
    }

    public static List<Star> SearchByAbsoluteStarSize(double lowerLimit, double upperLimit){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (lowerLimit < temporaryStar.absoluteStarSize && temporaryStar.absoluteStarSize < upperLimit) {
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
            //e.printStackTrace();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        return listOfStars;
    }

    public static List<Star> SearchByHemisphere(String hemisphere){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (temporaryStar.getHemisphere().equals(hemisphere)) {
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
            //e.printStackTrace();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        return listOfStars;
    }

    public static List<Star> SearchPotentialSupernova(){
        List<Star> listOfStars = new ArrayList<>();
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            Object object = null;
            while((object = inputStream.readObject()) != null){
                if(object instanceof Star) {
                    Star temporaryStar = (Star)object;
                    if (temporaryStar.mass > 1.44) {
                        listOfStars.add(temporaryStar);
                    }
                }
            }
            inputStream.close();
        }catch (EOFException e){
            System.out.println();
            //e.printStackTrace();
        }catch(IOException e){
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        }catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        return listOfStars;
    }
}