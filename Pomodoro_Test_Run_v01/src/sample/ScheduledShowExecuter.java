package sample;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class ScheduledShowExecuter extends Thread{

    private String[] args;
    private File show;
    private ScheduledShowGUI showGUI;

    ScheduledShowExecuter(String[] argus, File file, ScheduledShowGUI guis){
        args = argus;
        show = file;
        showGUI = guis;
    }


    public void run(){
            RunShows(show);
    }


    private void RunShows(File files){

        LocalDateTime time = LocalDateTime.now();
        long currentTime = time.toLocalTime().toSecondOfDay();

        long ShowStartTime;
        String hours;

        Timer timer;
        try {
            BufferedReader myReader = new BufferedReader(new FileReader(files));
            myReader.readLine();
            hours = myReader.readLine().split(" ")[0];
            ShowStartTime = Long.parseLong(hours.split(":")[0]) * 3600
                    + Long.parseLong(hours.split(":")[1]) * 60 + Long.parseLong(hours.split(":")[2]);
            System.out.println("current time: " + currentTime);
            System.out.println("Show time: " + ShowStartTime);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = format.parse(format.format(date));
            System.out.println(date);
            myReader.close();

            if (currentTime < ShowStartTime){

                ArrayList<Long> segments = new ArrayList<>();

                BufferedReader myReader2 = new BufferedReader(new FileReader(files));
                myReader2.readLine();
                String line;
                long totalDuration = 0;
                long indDuration;
                while ((line = myReader2.readLine()) != null){
                    if (line.trim().isEmpty()){continue;}
                    indDuration = ((SegmentTimeInSecs(line.split(" ")[1]) - SegmentTimeInSecs(line.split(" ")[0]))/1000);
                    segments.add(indDuration);
                    totalDuration = totalDuration + indDuration;
                }

                timer = new Timer();
                timer.schedule(new RunScheduledShow(files, args, totalDuration, segments, showGUI), (ShowStartTime*1000 - 2000) - currentTime*1000);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    private long SegmentTimeInSecs(String time){
        return ((Long.parseLong(time.split(":")[0])*3600) + (Long.parseLong(time.split(":")[1])*60) +
                (Long.parseLong(time.split(":")[2]))) * 1000;
    }
}
