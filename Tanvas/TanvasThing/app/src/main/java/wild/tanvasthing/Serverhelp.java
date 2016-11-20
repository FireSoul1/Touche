package wild.tanvasthing;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ColorModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

/**
 * Created by GbearTheGenius on 11/19/16.
 */

public class Serverhelp {
    private String host;
    private int port;

    static ArrayList<String> tags;
    static TreeMap<String, String> densities;
    Bitmap bitmap;


    static ClarifaiClient client;
    static String clientID, secret;
    Serverhelp(int port, String host, Bitmap bitmap) {
        this.port = port;
        this.host = host;
    }
    //send request to Clarifai
    public static void getTags(String path) {

        tags = new ArrayList<>();

        String ace = "yXPgoui487CyLJGUAaCIm3bcps3nuw";
        clientID = "oZlOBWReqGuggCv6UXgSEQUTapq2btRYx8yZWt0A";
        secret = "OLFv_3NhpUXuf2eOIEGiiL7fnTvJ-VF2_K8gg1MB";

        //intialize the client
        final ClarifaiClient client = new ClarifaiBuilder(clientID, secret)
                .client(new OkHttpClient())
                .buildSync();
        //get Image Tags
        final List<ClarifaiOutput<Concept>> rep =
                client.getDefaultModels()
                        .generalModel()
                        .predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(new File(path))))
                        .executeSync()
                        .get();
        String out = rep.toString();
        String err = "";
        int y =0;
        for(String gr : out.split("Concept")) {
            String[] t = gr.split(",");
            if(y > 1) {
                String end = t[1].substring(t[1].indexOf("=") + 1);
                err = err + end.replaceAll(" ", "-") + " ";
                tags.add(end);
            }
            y++;
        }
        Log.d("Clarifai:", err);

    }
    public static int getDensity(String path) {
        densities = new TreeMap<>();

        String ace = "yXPgoui487CyLJGUAaCIm3bcps3nuw";
        clientID = "oZlOBWReqGuggCv6UXgSEQUTapq2btRYx8yZWt0A";
        secret = "OLFv_3NhpUXuf2eOIEGiiL7fnTvJ-VF2_K8gg1MB";


        //intialize the client
        final ClarifaiClient client = new ClarifaiBuilder(clientID, secret)
                .client(new OkHttpClient())
                .buildSync();
        //get Image Tags
        final List<ClarifaiOutput<Color>> rep =
                client.getDefaultModels()
                        .colorModel()
                        .predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(new File(path))))
                        .executeSync()
                        .get();
        String out = rep.toString();
        Log.d("ServerHelper", out);
        String err = "";
        int y =0;
        //densities
        String curr = "";
        for(String gr : out.split("Color")) {
            String[] t = gr.split(",");
            if(y > 1 && y % 2 == 1) {
               // Log.d("ServerHelper", gr +" ");
                String end = t[1].substring(t[1].indexOf("value=")+6);
                err = err + end.replaceAll("\\}", "0").replaceAll("\\]", "0") + " ";
                densities.put(curr, end);
            }
            //colors
            if(y > 1 && y % 2 == 0) {
                curr = t[1].substring(t[1].indexOf("#"));
            }
            y++;
        }
        Log.d("ServerHelp", err);

        return 42;
    }
    public static Bitmap applyFilter(Bitmap u) {

        ImageHelper im = new ImageHelper();
        Bitmap is = im.Image_Segmentation(u,(float)((43*Math.PI)/180), (float)0.55);
        Bitmap gray = im.Grayscale(u);
        Bitmap fin = null;

        for(int y = 0; y < ImageHelper.tags.length; y++) {
            if(tags.contains(ImageHelper.tags[y])) {
                switch (ImageHelper.tags[y]) {
                    case "wood": fin = im.NoiseFilter(u); break;
                    case "metal": fin = im.Gaussian_Blur(u,100); break;
                    case "water": fin = im.Gaussian_Blur(u,3);break;
                    case "brick": fin = im.createContrast(u, 56);break;
                    case "cloth": fin = im.Gaussian_Blur(u, 2);break;
                }
            }
        }
        if(fin == null)
            fin = u;
        Bitmap ret = im.bitmap_special_crop(fin, is, gray, 0.3f);
        //reduce, reuse....
        //is.recycle();
        //u.recycle();
        //gray.recycle();
       // fin.recycle();

        return ret;
    }

    public int uploadImage(String path) {

        Socket sock = new Socket();
        try {
            Log.d("Sockets","Opening the socket --> ");
            sock.connect((new InetSocketAddress(host, port)));
            final File f = new File(
                    Environment.getExternalStorageDirectory()
                            + "/wifip2pshared-"
                            + System.currentTimeMillis() + ".jpg");
            File dirs  = new File(f.getParent());

            if (!dirs.exists())
                dirs.mkdirs();
            f.createNewFile();

            // send Data To Server
            OutputStream stream = sock.getOutputStream();
            FileInputStream file = new FileInputStream(
                    "/sdcard/samsung/Image/001" + ".jpg");


            file.close();

        } catch (Exception e) {

        }

        //the process worked
        return 42;
    }
    public int getnewImage() {


        //the process worked
        return 42;

    }

}
