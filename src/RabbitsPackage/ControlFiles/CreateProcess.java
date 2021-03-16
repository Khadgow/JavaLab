package RabbitsPackage.ControlFiles;

import AppletModules.MyField;

import java.util.TimerTask;

public class CreateProcess extends TimerTask {
    Habitat h;
    MyField field;
    int sec;
    int gameSec;
    int min;
    int milSec;

    public CreateProcess(Habitat h) {
        this.h = h;
    }
    public CreateProcess(Habitat h, CreateProcess bp, MyField field) {
        this.h = h;
        this.sec = bp.sec;
        this.min = bp.min;
        this.milSec = bp.milSec;
        this.gameSec = bp.gameSec;
        this.field = field;
    }
    public CreateProcess(Habitat h, MyField field) {
        this.h = h;
        this.field = field;
    }

    @Override
    public void run() {
        milSec++;
        if(milSec % 10 == 0){
            sec++;
            gameSec++;
            if (sec % 60 == 0) {
                min++;
                sec = 0;
            }
            h.update(gameSec);
        } else {
            field.paintWithoutRabbits();
        }
    }
    public int getTime(){
        return gameSec;
    }
    public int getMilTime(){
        return milSec;
    }
}
