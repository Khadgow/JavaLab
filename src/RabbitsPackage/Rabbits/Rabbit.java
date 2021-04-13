package RabbitsPackage.Rabbits;

import java.io.Serializable;

public abstract class Rabbit implements IBehaviour, Serializable {
    private int x;
    private int y;
    public int id;

    Rabbit(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }


    @Override
    public void move(int x, int y) {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
