package model.habitat;

import bornProcess.BornProcess;
import controller.Controller;
import model.rabbits.*;
import view.MyFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class Habitat {
    private int N1;
    private int N2;
    private int P1;
    private double K;
    private Controller controller;
    final private int WIDTH = 600;
    final private int HEIGHT = 600;
    private MyFrame myframe;
    final private String pathToOrdinary = "src/resources/Ordinary.png";
    final private String pathToAlbinos = "src/resources/Albinos.png";
    private ArrayList<Rabbit> rabbitsList = new ArrayList<>();
    private Timer timer = new Timer();
    private boolean bornProcessOn = false;
    BornProcess bornProcess = new BornProcess(this);

    public Habitat(int N1, int N2, int P1, double K, MyFrame myframe) {
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.K = K/100;
        this.myframe = myframe;
    }

    private Point generatePoint() {
        int x = (int) (Math.random() * (myframe.getWidth() - 299));
        int y = (int) (Math.random() * (myframe.getHeight() - 99));
        return new Point(x, y);
    }

    boolean isOrdinaryRabbitBorn(int N1, int P1, int time) {
        int probability = (int)(Math.random() * 100 + 1);
        return probability <= P1 && time % N1 == 0;
    }

    boolean isAlbinosRabbitBorn(int N2, double K, int time) {
        return time % N2 == 0 && AlbinosRabbit.numberOfAlbinos < (int) (Rabbit.countAllRabbits * K);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void update(int time) {
        AbstractFactory factory;

        controller.passTime(time);

        if (isOrdinaryRabbitBorn(N1, P1, time)) {
            factory = new AbstractOrdinaryFactory();
            Point randomPoint = generatePoint();
            Rabbit newRabbit = factory.rabbitBorn(randomPoint.x, randomPoint.y, pathToOrdinary);
            rabbitsList.add(newRabbit);
            controller.toPaint(rabbitsList);
        }

        if (isAlbinosRabbitBorn(N2, K, time)) {
            factory = new AbstractAlbinosFactory();
            Point randomPoint = generatePoint();
            Rabbit newRabbit = factory.rabbitBorn(randomPoint.x, randomPoint.y, pathToAlbinos);
            rabbitsList.add(newRabbit);
            controller.toPaint(rabbitsList);
        }
    }

    public void startBorn() {
        bornProcessOn = true;
        timer.schedule(bornProcess, 0, 1000);

    }

    public void stopBorn() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        bornProcess = new BornProcess(this ,bornProcess);
        bornProcessOn = false;
    }

    public void stopBornFinally() {
        timer.cancel();
        timer.purge();
        timer = new Timer();

        bornProcessOn = false;
    }

    public void refreshRabbitPopulation() {
        OrdinaryRabbit.numberOfOrdinary = 0;
        AlbinosRabbit.numberOfAlbinos = 0;
        Rabbit.countAllRabbits = 0;
        rabbitsList = new ArrayList<>();
    }

    public void confifureController(Controller controller) {
        this.controller = controller;
    }

    public boolean isBornProcessOn() {
        return bornProcessOn;
    }
}
