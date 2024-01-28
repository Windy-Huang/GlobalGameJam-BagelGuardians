package Textbox;


import java.awt.*;
import java.util.Random;

public class Textbox {

    public static final int width = 150;
    public static final int height= 75;
    public int x;
    public int y;
    public boolean isClicked;

    //speed at which box moves
    private int dY = 12;
    private int dX = 12;

    private int r;
    private Random rand;

    private int boxConstant = 100;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public double screenWidth = screenSize.getWidth();
    public double screenHeight = screenSize.getHeight();

    public double relativeMouseCoordinateHelperX = (screenWidth - 800) / 2;
    public double relativeMouseCoordinateHelperY = (screenHeight - 650) / 2;


    public Textbox() {
        this.x = 400;
        this.y = 300;
        this.isClicked = true;
    }

    public void move() {
        //to correct mouse coordinate
        double mouseX = MouseInfo.getPointerInfo().getLocation().x - relativeMouseCoordinateHelperX;
        double mouseY = MouseInfo.getPointerInfo().getLocation().y - relativeMouseCoordinateHelperY;

        //System.out.println("(" + mouseX + ", " + mouseY + ")");
        System.out.println("(" + x + ", " + y + ")");
        //mouse approaching box from left
        //left top
        if ((((this.x - (width/2) - mouseX) < boxConstant) && ((this.x - (width/2) - mouseX) > 0))
        && (((this.y - (height/2) - mouseY) < boxConstant) && ((this.y - (height/2) - mouseY) > 0)))
        {
            this.x += this.dX;
            this.y += this.dY;
        //left bottom

        } else if ((((this.x - (width/2) - mouseX) < boxConstant) && ((this.x - (width/2) - mouseX) > 0))
                && (((mouseY - (this.y + (height/2))) < boxConstant) && ((mouseY - (this.y + (height/2))) > 0))) {
            this.x += this.dX;
            this.y -= this.dY;

        }



        //right top
        if ((((mouseX - (this.x + (width/2))) < boxConstant) && ((mouseX - (this.x + (width/2))) > 0))
        && (((this.y - (height/2) - mouseY) < boxConstant) && ((this.y - (height/2) - mouseY) > 0))) {
            this.x -= this.dX;
            this.y += this.dY;
        //right bottom
        } else if ((((mouseX - (this.x + (width/2))) < boxConstant) && ((mouseX - (this.x + (width/2))) > 0))
        && (((mouseY - (this.y + (height/2))) < boxConstant) && ((mouseY - (this.y + (height/2))) > 0))) {
            this.x -= this.dX;
            this.y -= this.dY;
        }

        //just left
        if ((((this.x - (width/2) - mouseX) < boxConstant) && ((this.x - (width/2) - mouseX) > 0))
        && (mouseY > (this.y - (height/2))) && (mouseY < (this.y + (height/2)))) {
            this.x += this.dX;
        //just right
        } else if ((((mouseX - (this.x + (width/2))) < boxConstant) && ((mouseX - (this.x + (width/2))) > 0))
        && (mouseY > (this.y - (height/2))) && (mouseY < (this.y + (height/2)))) {
            this.x -= this.dX;
        }

        //just top
        if ((((this.y - (height/2) - mouseY) < boxConstant) && ((this.y - (height/2) - mouseY) > 0))
        && (mouseX > (this.x - (width/2))) && (mouseX < (this.x + (width/2)))) {
            this.y += this.dY;
        //just bottom
        } else if ((((mouseY - (this.y + (height/2))) < boxConstant) && ((mouseY - (this.y + (height/2))) > 0))
        && (mouseX > (this.x - (width/2))) && (mouseX < (this.x + (width/2)))) {
            this.y -= this.dY;
        }

    }

    public void handleBoundary() {

        this.rand = new Random();
        r= rand.nextInt(2);

        if (this.y < 0) {
            if (r == 0) {
                this.y = 600-(height/2)-boxConstant;
            } else {
                this.y = 300;
            }
        } else if ((this.y + height/2) > 600) {
            if (r == 0) {
                this.y = boxConstant+(height/2);
            } else {
                this.y = 300;
            }
        }

        if (this.x < 0) {
            if (r == 0) {
                this.x = 800-(width/2)-boxConstant;
            } else {
                this.x = 400;
            }

        } else if ((this.x + width/2) > 800) {
            if (r == 0) {
                this.x = boxConstant+(width/2);
            } else {
                this.x = 400;
            }
        }
    }

    // EFFECTS: convert isClick to true when box is clicked
    public void clickBox() {}

    public void drawTextBox (Graphics g) {
        g.drawRect(this.x - width / 2, this.y - height / 2, width, height);
    }



}
