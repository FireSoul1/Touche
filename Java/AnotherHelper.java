package tanvas.purdue.tanvasproject;

import android.graphics.Color;

/**
 * Created by Blake Wilson on 11/19/2016.
 */
public class AnotherHelper {

    public float[] HexToHue(String hex){
        int rgbcolor = Color.parseColor(hex);
        float[] hsv = new float[3];
        Color.colorToHSV(rgbcolor,hsv);
        return hsv;
    }
}
