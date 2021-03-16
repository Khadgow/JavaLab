package RabbitsPackage.ControlFiles;

import RabbitsPackage.Rabbits.AlbinosRabbit;
import RabbitsPackage.Rabbits.OrdinaryRabbit;
import RabbitsPackage.Rabbits.Rabbit;
import AppletModules.MyField;
import AppletModules.MyFrame;

import java.util.ArrayList;
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

    public void stopCreateProcess() {
        habitat.stopCreate();
    }
    public void stopCreateProcessFinally() {
        habitat.stopCreateFinally();
        refreshRabbitPopulation();
    }

    public void startCreateProcess() {
        habitat.startCreate();
    }

    public boolean isCreateProcessOn() {
        return habitat.isCreateProcessOn();
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

    public MyField getM() {
        return m;
    }
}
