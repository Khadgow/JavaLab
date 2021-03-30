package RabbitsPackage.Rabbits;

import RabbitsPackage.ControlFiles.Controller;
import RabbitsPackage.ControlFiles.CreateProcess;

import java.util.Vector;

public class OrdinaryRabbitAI  extends BaseAI{
    int dx = 5;
    int dy = 5;
    int directionY = Math.random()<0.5 ? 1 : -1;
    int directionX = Math.random()<0.5 ? 1 : -1;
    CreateProcess process;
    Controller controller;
    public OrdinaryRabbitAI(Vector<Rabbit> rabbits, CreateProcess process, Controller controller) {
        super(rabbits);
        this.process = process;
        this.controller = controller;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int time = process.getMilTime();
                if(time % 5 == 0){
                    if (Math.random() < 0.5) {
                        directionX = 1;
                    } else {
                        directionX = -1;
                    }
                    if (Math.random() < 0.5) {
                        directionY = 1;
                    } else {
                        directionY = -1;
                    }
                }
                for (Rabbit rabbit : rabbits) {
                    int x = rabbit.getX();
                    int y = rabbit.getY();
                    rabbit.setX(x + (dx * directionX));
                    rabbit.setY(y + (dy * directionY));
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

//while (going) {
//synchronized (rabbits) {
//int time = process.getTime();
//                for (int i = 0; i < rabbits.size(); i++) {
//                    Rabbit rabbit = rabbits.get(i);
//                    if (rabbit instanceof OrdinaryRabbit) {
//                        if (Math.random() < 0.5) {
//                            directionX = 1;
//                        } else {
//                            directionX = -1;
//                        }
//                        if (Math.random() < 0.5) {
//                            directionY = 1;
//                        } else {
//                            directionY = -1;
//                        }
//                        int x = rabbit.getX();
//                        int y = rabbit.getY();
//                        rabbit.setX(x + (dx * directionX));
//                        rabbit.setY(y + (dy * directionY));
//                    }
//                }
//}
//       }