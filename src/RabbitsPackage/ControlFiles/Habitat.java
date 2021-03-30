package RabbitsPackage.ControlFiles;

import RabbitsPackage.Rabbits.*;
import AppletModules.MyFrame;

import java.awt.*;
import java.util.*;

public class Habitat {
    private int N1;
    private int N2;
    private int P1;
    private double K;
    private Controller controller;
    final private int WIDTH = 600;
    final private int HEIGHT = 600;
    private MyFrame myframe;
    private volatile Vector<Rabbit> rabbitsList = new Vector<>();
    private Timer timer = new Timer();
    private boolean createProcessOn = false;
    CreateProcess createProcess = new CreateProcess(this);
    private int livingTimeOrdinary;
    private int livingTimeAlbinos;
    private AlbinosRabbitAI albinosAI;
    private OrdinaryRabbitAI ordinaryAI;
    private TreeSet<String> idList = new TreeSet<>();
    private HashMap<String,String> timeList = new HashMap<>();
    public int totalTime;

    public  Habitat(int N1, int N2, int P1, double K, MyFrame myframe, int livingTimeOrdinary, int livingTimeAlbinos) {
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.K = K/100;
        this.myframe = myframe;
        this.livingTimeOrdinary = livingTimeOrdinary;
        this.livingTimeAlbinos = livingTimeAlbinos;
    }

    private Point generatePoint() {
        int x = (int) (Math.random() * (myframe.getWidth() - 299));
        int y = (int) (Math.random() * (myframe.getHeight() - 99));
        return new Point(x, y);
    }

    boolean isOrdinaryRabbitCreate(int N1, int P1, int time) {
        int probability = (int)(Math.random() * 100 + 1);
        return probability <= P1 && time % N1 == 0;
    }

    boolean isAlbinosRabbitCreate(int N2, double K, int time) {
        return time % N2 == 0 && AlbinosRabbit.numberOfAlbinos < (int) (Rabbit.countAllRabbits * K);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void update(int time) {

        controller.passTime(time);

        totalTime = time;

        Vector<String> removeId = new Vector<>();
        Iterator<String> iter = idList.iterator();
        while (iter.hasNext()){
            String id = iter.next();
            if(Integer.parseInt(timeList.get(id)) - time + livingTimeOrdinary <=0 || Integer.parseInt(timeList.get(id)) - time + livingTimeAlbinos <=0){
                for (int i = 0; i< rabbitsList.size(); i++){
                    Rabbit rabbit = rabbitsList.get(i);
                    if(rabbit.id == Integer.parseInt(id)){
                        if((rabbit instanceof OrdinaryRabbit && Integer.parseInt(timeList.get(id)) - time + livingTimeOrdinary <=0)
                                || (rabbit instanceof AlbinosRabbit && Integer.parseInt(timeList.get(id)) - time + livingTimeAlbinos <=0)){
                            timeList.remove(id);
                            removeId.add(id);
                            rabbitsList.remove(rabbit);
                        }
                    }
                }

            }
        }

        for (int i = 0; i< removeId.size(); i++){
            String id = removeId.get(i);
            idList.remove(id);
        }
        removeId.clear();

        if (isOrdinaryRabbitCreate(N1, P1, time)) {
            int id = (int)(Math.random()*10000);
            while(idList.contains(Integer.toString(id))){
                id = (int)(Math.random()*10000);
            }
            idList.add(Integer.toString(id));
            timeList.put(Integer.toString(id), Integer.toString(time));

            idList.add(Integer.toString(id));
            Point randomPoint = generatePoint();
            Rabbit newRabbit = new OrdinaryRabbit(randomPoint.x, randomPoint.y, id);
            rabbitsList.add(newRabbit);
            controller.toPaint(rabbitsList);
        }

        if (isAlbinosRabbitCreate(N2, K, time)) {
            int id = (int)(Math.random()*10000);
            while(idList.contains(Integer.toString(id))){
                id = (int)(Math.random()*10000);
            }
            idList.add(Integer.toString(id));
            timeList.put(Integer.toString(id), Integer.toString(time));

            Point randomPoint = generatePoint();
            Rabbit newRabbit = new AlbinosRabbit(randomPoint.x, randomPoint.y, id);

            rabbitsList.add(newRabbit);
            controller.toPaint(rabbitsList);
        }
    }

    public void startCreate() {
        createProcessOn = true;
        timer.schedule(createProcess, 0, 100);
        ordinaryAI = new OrdinaryRabbitAI(rabbitsList, createProcess, controller);
        ordinaryAI.continue_();
        ordinaryAI.start();
        albinosAI = new AlbinosRabbitAI(rabbitsList, createProcess);
        albinosAI.continue_();
        albinosAI.start();
    }

    public void stopCreate() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        createProcess = new CreateProcess(this , createProcess, controller.getM());
        createProcessOn = false;
    }

    public void stopCreateFinally() {
        timer.cancel();
        timer.purge();
        timer = new Timer();

        createProcessOn = false;
    }

    public void refreshRabbitPopulation() {
        OrdinaryRabbit.numberOfOrdinary = 0;
        AlbinosRabbit.numberOfAlbinos = 0;
        Rabbit.countAllRabbits = 0;
        rabbitsList = new Vector<Rabbit>();
    }

    public void confifureController(Controller controller) {
        this.controller = controller;
        createProcess = new CreateProcess(this, controller.getM());
    }
    public void switchAI(){
        ordinaryAI.changeState();
        albinosAI.changeState();
    }
    public void changeSettings(String[] newSettings){
        this.P1 = Integer.parseInt(newSettings[0]);
        this.N1 = Integer.parseInt(newSettings[1]);
        this.N2 = Integer.parseInt(newSettings[2]);
        this.K = Double.parseDouble(newSettings[3]);
        this.livingTimeOrdinary = Integer.parseInt(newSettings[4]);
        this.livingTimeAlbinos = Integer.parseInt(newSettings[5]);
        if(newSettings[6].equals("High")){
            ordinaryAI.setPriority(Thread.MAX_PRIORITY);
        } else if (newSettings[6].equals("Low")) {
            ordinaryAI.setPriority(Thread.MIN_PRIORITY);
        } else {
            ordinaryAI.setPriority(Thread.NORM_PRIORITY);
        }
        if(newSettings[7].equals("High")){
            albinosAI.setPriority(Thread.MAX_PRIORITY);
        } else if (newSettings[7].equals("Low")) {
            albinosAI.setPriority(Thread.MIN_PRIORITY);
        } else {
            albinosAI.setPriority(Thread.NORM_PRIORITY);
        }
    }

    public void changeSettings(int P1, int N1, int N2, double K, int livingTimeOrdinary, int livingTimeAlbinos){
        this.P1 = P1;
        this.N1 = N1;
        this.N2 = N2;
        this.K = K;
        this.livingTimeOrdinary = livingTimeOrdinary;
        this.livingTimeAlbinos = livingTimeAlbinos;
    }

    public boolean isCreateProcessOn() {
        return createProcessOn;
    }
    public HashMap<String,String> getTimeList() {
        return timeList;
    }
    public TreeSet<String> getIdList() {
        return idList;
    }
    public Vector<Rabbit> getRabbitsList() {
        return rabbitsList;
    }
    public AlbinosRabbitAI getAlbinosAI() { return albinosAI; }
    public OrdinaryRabbitAI getOrdinaryAI() { return ordinaryAI; }
}
