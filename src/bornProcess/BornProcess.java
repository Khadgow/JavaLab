package bornProcess;

import model.habitat.Habitat;

import java.util.TimerTask;

//BornProcess - реализует таймер, вызывает метод update у habitat
public class BornProcess extends TimerTask {
    Habitat h;
    int sec;
    int gameSec;
    int min;

    public BornProcess(Habitat h) {
        this.h = h;
    }
    public BornProcess(Habitat h, BornProcess bp) {
        this.h = h;
        this.sec = bp.sec;
        this.min = bp.min;
        this.gameSec = bp.gameSec;
    }

    @Override
    public void run() {
        sec++;
        gameSec++;
        if (sec % 60 == 0) {
            min++;
            sec = 0;
        }
        h.update(gameSec);
    }
}
