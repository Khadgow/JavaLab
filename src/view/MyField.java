package view;

import controller.Controller;
import model.rabbits.AlbinosRabbit;
import model.rabbits.OrdinaryRabbit;
import model.rabbits.Rabbit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyField extends JPanel {
    ArrayList<Rabbit> rabbits = new ArrayList<>();
    Controller controller;

    public MyField() {
        super();
    }

    public void paintRabbit(ArrayList<Rabbit> rabbits) {
        this.rabbits = rabbits;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < rabbits.size(); i++) {
            Rabbit rabbit = rabbits.get(i);
            if (rabbit instanceof OrdinaryRabbit){
                g.drawImage(OrdinaryRabbit.image, rabbit.getX(), rabbit.getY(), null);
            }
            else {
                g.drawImage(AlbinosRabbit.image, rabbit.getX(), rabbit.getY(), null);
            }
        }
    }

    public void configureController(Controller controller) {
        this.controller = controller;
    }
}
