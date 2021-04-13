package AppletModules;

import RabbitsPackage.ControlFiles.Controller;
import RabbitsPackage.Rabbits.AlbinosRabbit;
import RabbitsPackage.Rabbits.OrdinaryRabbit;
import RabbitsPackage.Rabbits.Rabbit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MyField extends JPanel {
    Vector<Rabbit> rabbits = new Vector<>();
    Controller controller;
    private Image imageAlbinos;
    private Image imageOrdinary;
    public MyField() {
        super();
        try {
            imageAlbinos = ImageIO.read(new File("src/resources/Albinos.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            imageOrdinary = ImageIO.read(new File("src/resources/Ordinary.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintRabbit(Vector<Rabbit> rabbits) {
        this.rabbits = rabbits;
        repaint();
    }
    public void paintWithoutRabbits(){
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < rabbits.size(); i++) {
            Rabbit rabbit = rabbits.get(i);
            if (rabbit instanceof OrdinaryRabbit){
                g.drawImage(imageOrdinary, rabbit.getX(), rabbit.getY(), null);
            }
            else {
                g.drawImage(imageAlbinos, rabbit.getX(), rabbit.getY(), null);
            }
        }
    }

    public void configureController(Controller controller) {
        this.controller = controller;
    }
}
