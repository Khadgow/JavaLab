package RabbitsPackage.Rabbits;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class OrdinaryRabbit extends Rabbit implements Serializable {
    public static int numberOfOrdinary = 0;
    static public Image image;

    static {
        try {
            image = ImageIO.read(new File("src/resources/Ordinary.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OrdinaryRabbit(int x, int y, int id) {
        super(x, y, id);
        numberOfOrdinary++;
        countAllRabbits++;
    }
}
