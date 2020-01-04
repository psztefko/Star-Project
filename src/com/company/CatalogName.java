package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class CatalogName {
    protected String[] greekAlphabet = new String[]{"ALPHA", "BETA", "GAMMA", "DELTA", "EPSILON", "ZETA", "ETA", "THETA", "IOTA", "KAPPA", "LAMBDA", "MU", "NU", "XI", "OMICRON", "PI", "RHO", "SIGMA", "TAU", "UPSILON", "PHI", "CHI", "PSI", "OMEGA"};
    List<String> ListOfConstellations;

    public CatalogName() {

    }

    public String[] SetCatalogName(Star star){
        int counter = 0;
        try{
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream("stars.txt"));
            while(inputStream.readObject() != null){
                Star temporaryStar = (Star)inputStream.readObject();
                if(star.constellation.toLowerCase().equals(temporaryStar.constellation)){
                    counter++;
                }
            }
            inputStream.close();
        }catch(IOException e){
            System.out.printf("IOException is caught");
        }catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException is caught");
        }
        String[] catalogName = new String[]{star.constellation, greekAlphabet[counter]};
        return catalogName;
    }

}
