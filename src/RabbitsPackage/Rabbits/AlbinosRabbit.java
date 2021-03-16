package RabbitsPackage.Rabbits;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AlbinosRabbit extends Rabbit {
    public static int numberOfAlbinos = 0;
    static public Image image;
    boolean going = true;
    static {
        try {
            image = ImageIO.read(new File("src/resources/Albinos.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AlbinosRabbit(int x, int y, int id) {
        super(x, y, id);
        numberOfAlbinos++;
        countAllRabbits++;
    }
}
