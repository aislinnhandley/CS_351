//Aislinn Handley
//CS 351L 002

//Domino.java

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Domino {
    private int side_one;
    private int side_two;
    private boolean isDouble;
    private String fileName;
    private ImageView dominoPic;

    public Domino(int s1, int s2) {
        side_one = s1;
        side_two = s2;
        isDouble = false;
        if (side_one == side_two) {
            isDouble = true;
        }
        fileName = (Integer.toString(side_one)+Integer.toString(side_two)+".png");
        dominoPic = new ImageView(new Image(fileName, 100,50,false,false));
    }

    public int getSideOne() {
        return side_one;
    }

    public int getSideTwo() {
        return side_two;
    }

    public boolean getIsDouble(){
        return isDouble;
    }

    //public String getFileName(){return fileName;}

    public ImageView getImage(){return dominoPic;}

    public void setImage(ImageView pic) {
        dominoPic = pic;
    }
}