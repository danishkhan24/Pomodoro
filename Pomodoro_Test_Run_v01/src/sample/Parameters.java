package sample;

import java.util.ArrayList;

class Parameters {
    static String ShowName;
    static ArrayList<Long> segments;
    static int ShowDuration;

    Parameters(String name, ArrayList<Long> list, long sd){
        ShowName = name;
        segments = list;
        ShowDuration = (int)sd;
    }
}
