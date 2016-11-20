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

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

/**
 * Created by GbearTheGenius on 11/19/16.
 */

public class Serverhelp {
    private String host;
    private int port;
    private ArrayList<String> tags;
    Bitmap bitmap;


    static ClarifaiClient client;
    static String clientID, secret;
    Serverhelp(int port, String host, Bitmap bitmap) {
        this.port = port;
        this.host = host;
    }
    //send request to Clarifai
    public static void getTags(String path) {

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
        for(ClarifaiOutput<Concept> t : rep) {

        }

        Log.d("Clarifai:", out);

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
