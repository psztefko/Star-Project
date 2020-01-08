package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Star implements Serializable {

    //protected enum GreekAlphabet {ALPHA, BETA, GAMMA, DELTA, EPSILON, ZETA, ETA, THETA, IOTA, KAPPA, LAMBDA, MU,NU,XI,OMICRON,PI,RHO,SIGMA,TAU,UPSILON,PHI,CHI,PSI,OMEGA}
    protected String[] greekAlphabet = new String[]{"ALPHA", "BETA", "GAMMA", "DELTA", "EPSILON", "ZETA", "ETA", "THETA", "IOTA", "KAPPA", "LAMBDA", "MU", "NU", "XI", "OMICRON", "PI", "RHO", "SIGMA", "TAU", "UPSILON", "PHI", "CHI", "PSI", "OMEGA"};
    protected String name;
    protected double observedStarSize;                          //wielkość stosowana do określenia blasku gwiazd wyrażana w jednostkach magnitudo. Niższa wartość oznacza większą jasność gwiazdy. Zakładamy, iż minimalna dopuszczalna wielkość gwiazdowa wynosi -26.74 (wartość dla Słońca). Przyjmujemy, iż maksymalna wartość magnitudo wynosi 15.00.
    protected double distanceInLightYears;
    protected Declination declination;
    protected RightAscension rightAscension;
    protected String constellation;
    protected double absoluteStarSize;                          //wartość magnitudo, jaką ma gwiazda z określonej odległości. Istnieje ścisła zależność pomiędzy obserwowaną a absolutną wielkością gwiazdową wyrażona wzorem: M = m − 5· log10r + 5 (logarytm przy podstawie 10 z r), gdzie m to obserwowana wielkość gwiazdowa, a r to odległość od gwiazdy wyrażona w parsekach. Przyjmujemy, iż 1 parsek to 3.26 roku świetlnego.
    protected boolean isNothernhemisphere;
    protected double temperature;                               //(W stopniach Celsjusza) Przyjmujemy, iż minimalna temperatura gwiazdy wynosi 2000 stopni, górna granica nie występuje.
    protected double mass;                                      //(podana w odniesieniu do masy Słońca). Przyjmujemy, iż minimalna masa gwiazdy wynosi 0.1 masy Słońca, natomiast maksymalna dopuszczalna masa wynosić będzie 50.
    protected String[] catalogName;                             //nazwa katalogowa[litera alfabetu greckiego + nazwa gwiazdozbioru]
    protected List<Double> starsBrightnessList = new ArrayList<Double>();


    public Star(String name, double observedStarSize, double distanceInLightYears, String constellation, String hemisphere, double temperature, double mass, RightAscension rightAscension, Declination declination, String filePath) {
        this.name = CheckName(name);
        this.observedStarSize = CheckMagnitudo(observedStarSize);
        this.distanceInLightYears = distanceInLightYears;
        this.constellation = constellation;
        this.absoluteStarSize = SetAbsoluteStarSize();
        this.isNothernhemisphere = CheckHemisphere(hemisphere);
        this.temperature = CheckTemperature(temperature);
        this.mass = CheckMass(mass);
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.catalogName = SetCatalogName(filePath);
    }



    //Conditions for star to exist

    protected String CheckName(String name){
        int grandLetters = 0;
        int numbers = 0;

        for (int i = 0; i < 3; i++) {           //loop counting grand letters in star name
            if((byte)name.charAt(i) > 64 && (byte)name.charAt(i) < 91){
                grandLetters++;
            }
        }
        for (int i = 0; i < 4; i++) {           //loop counting numbers in star name
            if((byte)name.charAt(3 + i) > 47 && (byte)name.charAt(3 + i) < 58){
                numbers++;
            }
        }

        if(numbers != 4 || grandLetters != 3){
            throw new IllegalArgumentException("Name is incorrect");
        }else{
            return name;
        }
    }

    protected double CheckMagnitudo(double observedStarSize){
        if(observedStarSize < -26.74 && observedStarSize > 15.00){
            throw new IllegalArgumentException("Incorrect observed star size (magnitudo)");
        }
        return observedStarSize;
    }

    protected double SetAbsoluteStarSize(){
        double parsec = distanceInLightYears * 3.26;
        return observedStarSize - 5*Math.log10(parsec) + 5;
    }

    protected boolean CheckHemisphere(String hemisphere){
        if(hemisphere.toUpperCase().equals("PN")){
            return true;
        }else if(hemisphere.toUpperCase().equals("PD")){
            return  false;
        }else throw new IllegalArgumentException("Incorrect hemisphere value");
    }

    protected double CheckTemperature(double temperature){
        if(temperature < 2000){
            throw new IllegalArgumentException("Temperature must be over 2000°C");
        }
        return temperature;
    }

    protected double CheckMass(double mass){
        if(mass < 0.1 && mass > 50){
            throw new IllegalArgumentException("Incorrect mass");
        }
        return mass;
    }


    protected String[] SetCatalogName(String filePath) {
        int counter = 0;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
            Object object = null;
            while ((object = inputStream.readObject()) != null) {
                if (object instanceof Star) {
                    Star temporaryStar = (Star) object;
                    if (constellation.toLowerCase().equals(temporaryStar.constellation.toLowerCase())) {
                        starsBrightnessList.add(temporaryStar.getObservedStarSize());
                    }
                }
            }
            inputStream.close();
        } catch (EOFException e) {
            System.out.println();
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.printf("IOException is caught in SetCatalogName; ");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException is caught in SetCatalogName; ");
        }

        //sorting list of stars brightness
        Collections.sort(starsBrightnessList);

        
        if(starsBrightnessList.size() > 0){
            Double temp = starsBrightnessList.get(0);
            for (int i = 0; i <= starsBrightnessList.size(); i++) {
                temp = starsBrightnessList.get(i);
                if(temp.equals(observedStarSize)){
                    counter = i;
                    break;
                }
            }
        }


        String[] catalogName = new String[]{constellation, greekAlphabet[counter]};
        return catalogName;
    }

    public String ToString(){

        return "Name: " + name + "\n" +
                "Observed star size: " + observedStarSize + "\n" +
                "Distance in light years: " + distanceInLightYears + "\n" +
                "Constellation: " + constellation + "\n" +
                "Hemisphere: " + getHemisphere() + "\n" +
                "Temperature: " + temperature + "\n" +
                "Mass: " + mass + "\n" +
                "Right ascension: " + rightAscension.getHours() + "." + rightAscension.getMinutes() + "." + rightAscension.getSeconds() + "\n" +
                "Declination: " + declination.getDegrees() + "." + declination.getMinutes() + "." + declination.getSeconds() + "\n" +
                "Catalog name: " + Arrays.toString(catalogName) + "\n";
    }


    //Getters


    public String getName() {
        return name;
    }

    public Double getObservedStarSize(){
        return (Double)observedStarSize;
    }

    public double getDistanceInLightYears() {
        return distanceInLightYears;
    }

    public Declination getDeclination() {
        return declination;
    }

    public RightAscension getRightAscension() {
        return rightAscension;
    }

    public String getConstellation() {
        return constellation;
    }

    public double getAbsoluteStarSize() {
        return absoluteStarSize;
    }

    public String getHemisphere() {
        if(isNothernhemisphere){
            return "PN";
        }else{
            return "PD";
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public double getMass() {
        return mass;
    }

    public String[] getCatalogName() {
        return catalogName;
    }
}

//co robi exception?

//musi załadować wszystkie gwiazdy
//porównać gwiazdy z tych samych konstelacji

/*      Zbuduj klasę Gwiazda posiadającą następujące pola:
        nazwa – dowolne oznaczenie gwiazdy. Przyjmujemy, iż nazwa każdej
        gwiazdy składa się z 3 dużych liter oraz 4 cyfr.
        nazwa katalogowa – nazwa katalogowa składa się litery alfabetu
        greckiego oraz nazwy gwiazdozbioru. Najjaśniejsza gwiazda w
        gwiazdozbiorze oznaczana jest jako alfa, kolejna jako beta i tak
        dalej. Na potrzeby projektu zakładamy, iż kolejne litery greckie
        nadawane są gwiazdom w takiej kolejności, w jakiej dodane zostały
        do gwiazdozbioru. Przykładowo, jeżeli w bazie istnieją 2 gwiazdy w
        gwiazdozbiorze Wolarza, to trzecia z nich otrzyma katalogową nazwę
        gamma Wolarza (nazwa składa się z litery greckiej oraz nazwy
        gwiazdozbioru, do którego została dodana).
        deklinacja – jedna ze współrzędnych astronomicznych przyjmująca
        wartości od 0 do 90 stopni dla gwiazd znajdujących się na półkuli
        północnej oraz 0 do -90 stopni dla gwiazd na półkuli południowej.
        Wartość podajemy jako xx stopni yy minut zz.zz sekund.
        rektascensja – druga współrzędna astronomiczna przyjmująca
        wartości od 00h do 24h. Wartość podajemy jako xx h yy m zz s.
        obserwowana wielkość gwiazdowa – wielkość stosowana do określenia
        blasku gwiazd wyrażana w jednostkach magnitudo. Niższa wartość
        oznacza większą jasność gwiazdy. Zakładamy, iż minimalna
        dopuszczalna wielkość gwiazdowa wynosi -26.74 (wartość dla
        Słońca). Przyjmujemy, iż maksymalna wartość magnitudo wynosi
        15.00.
        absolutna wielkość gwiazdowa – wartość magnitudo, jaką ma
        gwiazda z określonej odległości. Istnieje ścisła zależność pomiędzy
        obserwowaną a absolutną wielkością gwiazdową wyrażona wzorem:
        M = m − 5· log10r + 5 (logarytm przy podstawie 10 z r), gdzie m to
        obserwowana wielkość gwiazdowa, a r to odległość od gwiazdy
        wyrażona w parsekach. Przyjmujemy, iż 1 parsek to 3.26 roku
        świetlnego.
        odległość w latach świetlnych.
        gwiazdozbiór – gwiazdozbiór, w którym można zobaczyć daną
        gwiazdę.
        półkula PN/ PD – półkula na której można zobaczyć daną gwiazdę.
        temperatura (podana w stopniach celsjusza). Przyjmujemy, iż
        minimalna temperatura gwiazdy wynosi 2000 stopni, górna granica
        nie występuje.
        masa (podana w odniesieniu do masy Słońca). Przyjmujemy, iż
        minimalna masa gwiazdy wynosi 0.1 masy Słońca, natomiast
        maksymalna dopuszczalna masa wynosić będzie 50.
        W przypadku dodawania nowego obiektu należy uwzględnić wszystkie
        podane powyżej parametry, ich zakres oraz ewentualne zależności
        pomiędzy nimi.


*/
