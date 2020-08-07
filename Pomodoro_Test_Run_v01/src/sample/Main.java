package sample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main{
    private static ArrayList<ScheduledShowGUI> gui = new ArrayList<>();

    public static void main(String[] args) throws ParseException {
        ArrayList<ScheduledShowExecuter> sets = new ArrayList<>();
        ArrayList<File> todayShows;
        File dir = new File("./");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".rsh"));
        if (files == null){return;}
        todayShows = ShowsToday(files);
        int i = 0;

        for (File file: todayShows){
            gui.add(new ScheduledShowGUI());
            sets.add(new ScheduledShowExecuter(args, file, gui.get(i)));

            if (i != 0){sets.get(i-1).interrupt();}
            sets.get(i).run();

            System.out.println("waiting for join");
            System.out.println("joined");

            i++;
        }
        //System.exit(0);
    }

    private static ArrayList<File> ShowsToday(File[] files) throws ParseException {

        Date filedate;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<File> todayShows = new ArrayList<>();
        Date date = new Date();
        date = format.parse(format.format(date));

        for (File rshfile: files){
            try {
                BufferedReader myReader = new BufferedReader(new FileReader(rshfile));

                filedate = format.parse(myReader.readLine());
                myReader.close();

                if ((date.compareTo(filedate)) == 0){ todayShows.add(rshfile); }

            } catch (IOException | ParseException e) { e.printStackTrace(); }
        }
        return todayShows;
    }
}
