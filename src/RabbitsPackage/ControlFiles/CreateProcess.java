package RabbitsPackage.ControlFiles;

import java.util.TimerTask;

//BornProcess - реализует таймер, вызывает метод update у habitat
public class CreateProcess extends TimerTask {
    Habitat h;
    int sec;
    int gameSec;
    int min;

    public CreateProcess(Habitat h) {
        this.h = h;
    }
    public CreateProcess(Habitat h, CreateProcess bp) {
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
