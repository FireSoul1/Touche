package tanvas.purdue.tanvasproject;
/**
 * Created by Blake Wilson on 11/19/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.Random;

public class imagehelper {

    Bitmap final_image;

    String[] tags = new String[]{
            "wood",         //Grainy noise
            "brick",        //high contrast
            "glass",        //strong blur
            "metal",        //smooth blur
            "water",        //weak blur
            "plastic",      //weak blur
            "cloth"         //high resolution week noise
    };

    public static final int COLOR_MAX = 0x0A;

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

    public Bitmap Grayscale(Bitmap pre_image)
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
    public Bitmap ConverttoPNG(String path)
    {
        return BitmapFactory.decodeFile(path);
    }

    public Bitmap Engrave_Image(Bitmap src){

            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
            convMatrix.setAll(0);
            convMatrix.Matrix[0][0] = -2;
            convMatrix.Matrix[1][1] = 2;
            convMatrix.Factor = 1;
            convMatrix.Offset = 95;
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
    }

    String Select_Image(){
        return "PATH/";
    }

    public Bitmap Image_Segmentation(Bitmap src, float hue , float saturation ) {
        Bitmap input = Bitmap.createBitmap(src);
        int x = 0,
                    y = 0;
        int pix;
        int def_color = Color.argb(255,255,255,255);
        float[][] hsv = new float[input.getHeight()*input.getWidth()][3];

        for( x = 0; x < input.getWidth(); x++ ) {
            for( y = 0; y < input.getHeight(); y++ ) {
                pix = input.getPixel(x, y);
                input.setPixel(x,y,def_color);
                Color.colorToHSV(pix,hsv[input.getWidth()*y + x]);
            }
        }

            float maxDist2 = 0.4f*0.4f;

            // Adjust the relative importance of Hue and Saturation.
            // Hue has a range of 0 to 2*PI and Saturation from 0 to 1.
            float adjustUnits = (float)(Math.PI/2.0);

            // step through each pixel and mark how close it is to the selected color
            Bitmap output = Bitmap.createBitmap(input);


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

    public Bitmap final_texture(Bitmap[] haptic_bitmaps){
        int max_color = Color.argb(255,255,255,255);
        Bitmap out_haptic = Bitmap.createBitmap(haptic_bitmaps[0]);
        int k,x,y;
        for(k = 0; k < haptic_bitmaps.length; k++){
            for(x = 0; x < out_haptic.getWidth();x++){
                for(y = 0; y < out_haptic.getHeight(); y++){
                    if(haptic_bitmaps[k].getPixel(x,y) < max_color)
                        out_haptic.setPixel(x,y,haptic_bitmaps[k].getPixel(x,y));
                }
            }
        }
        return out_haptic;
    }

    public Bitmap bitmap_special_crop(Bitmap effect_bitmap,Bitmap region_bitmap,Bitmap back_end, float back_end_priority){
        int x,y;
        int max_color = Color.argb(255,255,255,255);
        int pixel_value = 0;
        Bitmap output = Bitmap.createBitmap(region_bitmap);
        for(x = 0; x < region_bitmap.getWidth();x++){
            for(y = 0; y < region_bitmap.getHeight(); y++){
                if(region_bitmap.getPixel(x,y) < max_color)
                    pixel_value = (int)Math.ceil((double)back_end.getPixel(x,y) * back_end_priority) + (int)Math.ceil((1-back_end_priority)*(double)effect_bitmap.getPixel(x,y));
                    output.setPixel(x,y,pixel_value);
            }
        }
        return output;
    }
    /*
    Gaussian Blur for different materials
     */
    public Bitmap Gaussian_Blur(Bitmap src,int intensity){
        {
            double[][] GaussianBlurConfig = new double[][] {
                    { 1, 2, 1 },
                    { 2, 4, 2 },
                    { 1, 2, 1 }
            };
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
            convMatrix.applyConfig(GaussianBlurConfig);
            convMatrix.Factor = intensity;
            convMatrix.Offset = 0;
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
        }

    }
    /*
    Grainy Noise filter
     */

    public static Bitmap createContrast(Bitmap src, double value) {

        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        double contrast = Math.pow((100 + value) / 100, 2);


        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {

                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);

                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }


                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    public static Bitmap NoiseFilter(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];

        source.getPixels(pixels, 0, width, 0, 0, width, height);

        Random random = new Random();

        int index = 0;

        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {

                index = y * width + x;

                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));

                pixels[index] |= randColor;
            }
        }

        Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }
    /*
    Miscellaneous functions for processes
     */
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




