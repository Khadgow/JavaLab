package RabbitsPackage.Rabbits;

import java.util.Vector;

public abstract class BaseAI extends Thread {

    protected final Vector<Rabbit> rabbits;
    boolean running = true;
    BaseAI(Vector<Rabbit> rabbits){
        this.rabbits = rabbits;
    }

    abstract public void run();
    public synchronized void changeState() {
        running = !running;
        notifyAll();
    }

    public synchronized void pause() {
        running = false;
        notifyAll();
    }

    public synchronized void continue_() {
        running = true;
        notifyAll();
    }

    public synchronized boolean isRunning() {
        return running;
    }
}
