package wild.tanvasthing;

/**
 * Created by GbearTheGenius on 11/19/16.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import static android.graphics.Color.*;

public class ImageHelper {

    Bitmap final_image;
   // Context context;

    ImageHelper(){
      //  context = con;

    }
    String Master_Process(){
        String path = Select_Image();                       //path to image

        if(path != null){
            Bitmap pre_image = ConverttoPNG(path);
            if(pre_image != null)                           //Error checking for png
            {
                Bitmap gray_image = Grayscale(pre_image);
                if(gray_image != null)                      //error checking for gray scale
                {
                    final_image = gray_image;
                    return "0";
                }
                else return "3";
            }
            else return "2";
        }
        else return "1";


    }
    Bitmap Grayscale(Bitmap pre_image)
    {
        int width = pre_image.getWidth(),
                height = pre_image.getHeight(),
                i = 0,
                j = 0,
                pix,
                r,g,b;
        Bitmap OutBitMap = Bitmap.createBitmap(pre_image);

        //sets the bitmap to mutable
        Bitmap mutable = OutBitMap.copy(Bitmap.Config.ARGB_8888, true);

        for (i = 0; i < width;i++){
            for(j = 0; j < height;j++){
                pix = pre_image.getPixel(i, j);
                r = red(pix);
                g = green(pix);
                b = blue(pix);

                r = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                g = b = r;
                int tmp = Color.rgb( r, g, b);
               // Log.d("What even is: ", "Data; "+tmp);
                mutable.setPixel(i,j, tmp);
            }
        }

        return mutable;
    }
    Bitmap ConverttoPNG(String path)
    {
        return BitmapFactory.decodeFile(path);
    }

    String Select_Image(){
        return "PATH/";
    }
    Bitmap Image_Segmentation(Bitmap src, float hue , float saturation ) {
        Bitmap input = Bitmap.createBitmap(src);
        int x = 0,
                y = 0;
        int pix;
        float[][] hsv = new float[input.getHeight()*input.getWidth()][3];

        for( x = 0; x < input.getWidth(); x++ ) {
            for( y = 0; y < input.getHeight(); y++ ) {
                pix = input.getPixel(x, y);
                Color.colorToHSV(pix,hsv[input.getWidth()*y + x]);
            }
        }
        // Convert into HSV


        // Euclidean distance squared threshold for deciding which pixels are members of the selected set
        float maxDist2 = 0.4f*0.4f;

        // Adjust the relative importance of Hue and Saturation.
        // Hue has a range of 0 to 2*PI and Saturation from 0 to 1.
        float adjustUnits = (float)(Math.PI/2.0);

        // step through each pixel and mark how close it is to the selected color
        Bitmap output = Bitmap.createBitmap(input.getWidth(),input.getHeight(),input.getConfig());


        for( y = 0; y < input.getHeight(); y++ ) {
            for( x = 0; x < input.getWidth(); x++ ) {

                // Hue is an angle in radians, so simple subtraction doesn't work
                float new_hue = (float)(((hsv[input.getWidth() * y + x][0] * (Math.PI) / 180.0F)));
                float dh = (float)dist(new_hue,hue);
                float ds = (hsv[input.getWidth() * y + x][1]-saturation)*adjustUnits;

                // this distance measure is a bit naive, but good enough for to demonstrate the concept
                float dist2 = dh*dh + ds*ds;
                if( dist2 <= maxDist2 ) {
                    output.setPixel(x,y,input.getPixel(x,y));
                }
            }
        }
        return output;

    }
    public static double minus( float angA, float angB ) {
        float diff = angA - angB;

        if( diff > Math.PI ) {
            return (2 * Math.PI) - diff;
        } else if( diff < -Math.PI )
            return -(Math.PI*2) - diff;

        return diff;
    }

    public static double dist( float angA, float angB ) {
        return Math.abs(minus(angA,angB));
    }

}