//Aislinn Handley
//CS 351L 002

//Boneyard.java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Boneyard {
    private Domino[] boneyard;
    private ArrayList<Integer> drawn;
    private boolean isEmpty;
    private Map<String, String> utfDominoes;

    public Boneyard() {
        utfDominoes = new HashMap<>();
        utfDominoes.put("00","1F031");
        utfDominoes.put("11","1F039");
        utfDominoes.put("22","1F041");
        utfDominoes.put("33","1F049");
        utfDominoes.put("44","1F051");
        utfDominoes.put("55","1F059");
        utfDominoes.put("66","1F061");
        utfDominoes.put("01","1F032");
        utfDominoes.put("10","1F038");
        utfDominoes.put("02","1F033");
        utfDominoes.put("20","1F03F");
        utfDominoes.put("03","1F034");
        utfDominoes.put("30","1F046");
        utfDominoes.put("04","1F035");
        utfDominoes.put("40","1F04D");
        utfDominoes.put("05","1F036");
        utfDominoes.put("50","1F054");
        utfDominoes.put("06","1F037");
        utfDominoes.put("60","1F05B");
        utfDominoes.put("12","1F03A");
        utfDominoes.put("21","1F040");
        utfDominoes.put("13","1F03B");
        utfDominoes.put("31","1F047");
        utfDominoes.put("14","1F03C");
        utfDominoes.put("41","1F04E");
        utfDominoes.put("15","1F03D");
        utfDominoes.put("51","1F055");
        utfDominoes.put("16","1F03E");
        utfDominoes.put("61","1F05C");
        utfDominoes.put("23","1F042");
        utfDominoes.put("32","1F048");
        utfDominoes.put("24","1F043");
        utfDominoes.put("42","1F050");
        utfDominoes.put("25","1F044");
        utfDominoes.put("52","1F056");
        utfDominoes.put("26","1F045");
        utfDominoes.put("62","1F05D");
        utfDominoes.put("34","1F04A");
        utfDominoes.put("43","1F050");
        utfDominoes.put("35","1F04B");
        utfDominoes.put("53","1F057");
        utfDominoes.put("36","1F04C");
        utfDominoes.put("63","1F05E");
        utfDominoes.put("45","1F052");
        utfDominoes.put("54","1F058");
        utfDominoes.put("46","1F053");
        utfDominoes.put("64","1F05F");
        utfDominoes.put("56","1F05A");
        utfDominoes.put("65","1F060");

        //constructor creates 28 domino objects
        //& adds them to our boneyard
        boneyard = new Domino[28];
        drawn = new ArrayList();
        int counter = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                Domino d = new Domino(i, j);
                boneyard[counter] = d;
                counter++;
            }
        }
        isEmpty = false;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public Domino drawOne(){
        boolean alreadyDrawn = true;
        do{
            int rand = (int) (Math.random() * 28); //return 0-27
            //debugging only
            //System.out.println("\nRandomly generated index val: " + rand );
            if(!drawn.contains(rand)){
                alreadyDrawn = false;
                drawn.add(rand);
                if(drawn.size() == boneyard.length){isEmpty = true;}
                return boneyard[rand];
            }
        }while(alreadyDrawn);
        System.out.println("You should never have gotten here");
        return null;
    }

    public Map getDictionary(){
        return utfDominoes;
    }
}