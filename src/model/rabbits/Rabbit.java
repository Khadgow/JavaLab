package model.rabbits;

public abstract class Rabbit implements IBehaviour {
    private int x;
    private int y;
    public int id;
    public static int countAllRabbits = 0;
    private String pathToImg;

    Rabbit(int x, int y, String pathToImg, int id) {
        this.x = x;
        this.y = y;
        this.pathToImg = pathToImg;
        this.id = id;
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
