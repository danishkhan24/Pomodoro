package sample;
import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class RunScheduledShow extends TimerTask {

    private File showFile;
    private String[] args;
    private long totalDuration;
    private ArrayList<Long> segments;
    private ScheduledShowGUI showGUI;

    RunScheduledShow(File file, String[] arg, long td, ArrayList<Long> segmentslist, ScheduledShowGUI GUI){
        showFile = file;
        args = arg;
        totalDuration = td;
        segments = segmentslist;
        showGUI = GUI;
    }


    @Override
    public void run() {
        Parameters parameters = new Parameters(showFile.getName(), segments, totalDuration);
        ScheduledShowGUI scheduledShowGUI = showGUI;
        scheduledShowGUI.execute(args);
    }
}
