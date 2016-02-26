package coderschoolasignment.todoapp;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Administrator on 25/02/2016.
 */
public class TypefaceChoser {
    public static Typeface getTypeface_softElegance(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"fonts/softElegance.ttf");
    }
    public static Typeface getTypeface_aircraft(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"fonts/aircraft.ttf");
    }


}
