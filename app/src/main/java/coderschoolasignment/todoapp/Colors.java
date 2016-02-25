package coderschoolasignment.todoapp;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 24/02/2016.
 */
public class Colors {

    private static ArrayList<String> colors;
    private static int count;
    public static String getInstance()
    {
        if(colors==null)
        {
            count = 0;
            colors = new ArrayList<String>();
            colors.add("#ee4653");
            colors.add("#ffc426");
            colors.add("#82c345");
            colors.add("#0195b9");
        }
        count++;
        if(count==4)
            count = 0;
        return colors.get(count);
    }

}

