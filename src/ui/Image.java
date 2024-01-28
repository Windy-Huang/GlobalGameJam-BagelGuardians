package ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Image {

    public ArrayList<BufferedImage> imagelist = new ArrayList<BufferedImage>();

    public Image() {
        BufferedImage img = null;
        String path;
        for (int i=0; i<33; i++){
            try {
                path = "/img" + Integer.toString(i) + ".png";
                img = ImageIO.read(getClass().getResourceAsStream(path));
            } catch(IOException e) {
                e.printStackTrace();
            }
            imagelist.add(img);
        }
    }

}

