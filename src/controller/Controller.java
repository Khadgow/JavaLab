package controller;

import model.habitat.Habitat;
import model.rabbits.AlbinosRabbit;
import model.rabbits.OrdinaryRabbit;
import model.rabbits.Rabbit;
import view.MyField;
import view.MyFrame;

import java.util.Vector;

//Controller - реалезует различные методы для field, habitat, frame
public class Controller {
    private MyField m;
    private Habitat habitat;
    private MyFrame frame;
    public Controller(MyField myField, Habitat habitat, MyFrame frame) {
        this.m = myField;
        this.habitat = habitat;
        this.frame = frame;
    }

    public void toPaint(Vector<Rabbit> rabbits) {
        m.paintRabbit(rabbits);
    }

    public void stopBornProcess() {
        habitat.stopBorn();
    }
    public void stopBornProcessFinally() {
        habitat.stopBornFinally();
        refreshRabbitPopulation();
    }

    public void startBornProcess() {
        habitat.startBorn();
    }

    public boolean isBornProcessOn() {
        return habitat.isBornProcessOn();
    }

    public int getOrdinaryRabbitsAmount() {
        return OrdinaryRabbit.numberOfOrdinary;
    }

    public int getAlbinosRabbitsAmount() {
        return AlbinosRabbit.numberOfAlbinos;
    }

    public int getAllRabbitsCount() {
        return Rabbit.countAllRabbits;
    }

    public void refreshRabbitPopulation() {
        habitat.refreshRabbitPopulation();
    }

    public void passTime(int time) {
        frame.updateTime(time);
    }

    public void configurateHabitat(Habitat habitat) {
        this.habitat = habitat;
    }
}
