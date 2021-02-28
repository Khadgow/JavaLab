package model.rabbits;

public class AbstractOrdinaryFactory implements AbstractFactory {
    @Override
    public Rabbit rabbitBorn(int x, int y, String pathToImg, int id) {
        return new OrdinaryRabbit(x, y, pathToImg, id);
    }
}
