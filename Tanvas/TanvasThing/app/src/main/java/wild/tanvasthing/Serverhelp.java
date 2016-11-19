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
import java.util.ArrayList;

/**
 * Created by GbearTheGenius on 11/19/16.
 */

public class Serverhelp {
    private String host;
    private int port;
    private ArrayList<String> tags;
    Bitmap bitmap;

    Serverhelp(int port, String host, Bitmap bitmap) {
        this.port = port;
        this.host = host;
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
