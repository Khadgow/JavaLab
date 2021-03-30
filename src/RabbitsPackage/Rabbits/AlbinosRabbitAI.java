package RabbitsPackage.Rabbits;

import RabbitsPackage.ControlFiles.CreateProcess;

import java.util.Vector;

public class AlbinosRabbitAI extends BaseAI{
    int speed = 1;
    int radius = 50;
    CreateProcess process;
    public AlbinosRabbitAI(Vector<Rabbit> rabbits, CreateProcess process){
        super(rabbits);
        this.process = process;
    }


    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int time = process.getMilTime();
                for (Rabbit rabbit : rabbits) {
                    if (rabbit instanceof AlbinosRabbit) {
                        int x = rabbit.getX();
                        int y = rabbit.getY();
                        rabbit.setX((int) (x + radius * Math.cos(speed * time)));
                        rabbit.setY((int) (y + radius * Math.sin(speed * time)));
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                continue;
            }

            synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                }
            }
        }
    }
}