/**
 * Created by Blake Wilson on 11/19/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class imagehelper {

    Bitmap final_image;
    void imagehelper(){

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

        for (i = 0; i < width;i++){
            for(j = 0; j < height;j++){
                pix = pre_image.getPixel(i, j);
                r = Color.red(pix);
                g = Color.green(pix);
                b = Color.blue(pix);

                r = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                g = b = r;
                OutBitMap.setPixel(i,j,Color.argb(Color.alpha(pix), r, g, b));
            }
    }
        return OutBitMap;
    }
    Bitmap ConverttoPNG(String path)
    {
        return BitmapFactory.decodeFile(path);
    }

    String Select_Image(){
        return "PATH/";
    }
}
