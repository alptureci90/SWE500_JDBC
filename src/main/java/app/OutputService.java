package main.java.app;

import java.util.ArrayList;

public class OutputService {

    public static final int SL(int currentMax, String str)
    {
        return 2 + Math.max(0, currentMax - str.length()) / 2;
    }

    public static final int SR(int currentMax, String str)
    {
        return 2 + Math.round(Math.max(0, currentMax - str.length()) / (float) 2);
    }
}
