package coderschoolasignment.todoapp;

import java.util.ArrayList;

/**
 * Created by Administrator on 24/02/2016.
 */
public class Colours {

    private static ArrayList<String> colors;
    private static int count;

    public static String getInstance() {
        if (colors == null) {
            count = 0;
            colors = new ArrayList<String>();
            colors.add("#ee4653");
            colors.add("#FFF59D");
            colors.add("#82c345");
            colors.add("#0195b9");
        }
        count++;
        if (count == 4)
            count = 0;
        return colors.get(count);
    }

    public static String getColor1() {
        return "#FFF59D";
    }

    public static String getColor2() {
        return "#ee4653";
    }

    public static String getColor3() {
        return "#82c345";
    }

    public static String getColor4() {
        return "#0195b9";
    }

    public static String getColor1_shadow1() {
        return "#5fFFF59D";
    }

    public static String getColor1_shadow2() {
        return "#5fee4653";
    }

    public static String getColor1_shadow3() {
        return "#5f82c345";
    }

    public static String getColor1_shadow4() {
        return "#5f0195b9";
    }


}

