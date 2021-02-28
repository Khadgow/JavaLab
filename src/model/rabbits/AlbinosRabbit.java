package model.rabbits;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AlbinosRabbit extends Rabbit {
    public static int numberOfAlbinos = 0;
    static public Image image;

    static {
        try {
            image = ImageIO.read(new File("src/resources/Albinos.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    AlbinosRabbit(int x, int y, String pathToImg, int id) {
        super(x, y, pathToImg, id);
        numberOfAlbinos++;
        countAllRabbits++;
    }
}
