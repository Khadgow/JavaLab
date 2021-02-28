package model.rabbits;

public interface AbstractFactory {
    Rabbit rabbitBorn(int x, int y, String pathToImg, int id);
}
