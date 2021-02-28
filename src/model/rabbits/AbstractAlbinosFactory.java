package model.rabbits;

public class AbstractAlbinosFactory implements AbstractFactory {
    @Override
    public Rabbit rabbitBorn(int x, int y, String pathToImg, int id) {
        return new AlbinosRabbit(x, y, pathToImg, id);
    }
}
