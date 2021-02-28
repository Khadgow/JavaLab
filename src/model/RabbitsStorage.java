package model;

import model.rabbits.Rabbit;

import java.util.Vector;


public class RabbitsStorage {
    private static RabbitsStorage instance;
    private Vector<Rabbit> rabbitsList;

    private RabbitsStorage() {
        this.rabbitsList = new Vector<>();
    }

    public static RabbitsStorage getInstance() {
        if (instance == null) {
            instance = new RabbitsStorage();
        }
        return instance;
    }

    public Vector<Rabbit> getRabbitsList() {
        return rabbitsList;
    }
}
