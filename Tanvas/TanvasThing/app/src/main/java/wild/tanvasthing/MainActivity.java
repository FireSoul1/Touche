package wild.tanvasthing;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import co.tanvas.haptics.service.app.*;
import co.tanvas.haptics.service.adapter.*;
import co.tanvas.haptics.service.err.HapticServiceAdapterException;
import co.tanvas.haptics.service.err.NativeHapticObjectException;
import co.tanvas.haptics.service.model.*;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    //for the red rectangle
    private HapticView mHapticView;
    private HapticTexture mHapticTexture;
    private HapticMaterial mHapticMaterial;
    private HapticSprite mHapticSprite;

    Context tempe;

    //for the pic that Blake sent
    private HapticView mHapticView1;
    private HapticTexture mHapticTexture1;
    private HapticMaterial mHapticMaterial1;
    private HapticSprite mHapticSprite1;


    SweetAlertDialog pDialog;
    TextView textTargetUri;
    ImageView targetImage;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog  = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(" \"Patience is a virtue\" ~some wise guy/girl");
        progressDialog.setProgress(0);
        progressDialog.show();

        textTargetUri = (TextView)findViewById(R.id.vieww);
        targetImage = (ImageView) findViewById(R.id.imageView1);
        targetImage.setAlpha(0.89f);

        //long mil = SystemClock.currentThreadTimeMillis();
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.overlay);
        ImageHelper imm = new ImageHelper();
        targetImage.setImageBitmap(temp);
        progressDialog.dismiss();
        lv = (ListView) findViewById(R.id.listView2);


        //Log.d("Time:: ", SystemClock.currentThreadTimeMillis() - mil +"");

        //mil = SystemClock.currentThreadTimeMillis();
        //targetImage = (ImageView) findViewById(R.id.imageView1);
       // Bitmap temp1 = imm.Image_Segmentation(temp,(float)((43*Math.PI)/180), (float)0.55);
        //targetImage.setImageBitmap(temp1);
        //Log.d("Time:: ", SystemClock.currentThreadTimeMillis() - mil +"");

//        targetImage = (ImageView) findViewById(R.id.imageView2);
//        Bitmap temp2 = imm.Gaussian_Blur(temp, 16);
//        targetImage.setImageBitmap(temp2);
//
//        targetImage = (ImageView) findViewById(R.id.imageView4);
//        Bitmap temp3 = imm.Grayscale(temp);
//        targetImage.setImageBitmap(temp3);
//
//        targetImage = (ImageView) findViewById(R.id.imageView5);
//        Bitmap temp5 = imm.NoiseFilter(temp);
//        targetImage.setImageBitmap(temp5);






        //create new files
//        Bitmap ty;
//        ImageHelper im = new ImageHelper();
//        for(int u = 0; u < 3; u++) {
//
//            int id = this.getResources().getIdentifier("chair" + (u+1),"drawable", this.getPackageName());
//            ty = BitmapFactory.decodeResource(getResources(), id);
//            createNewFile(ty, u, "stand");
//            //run Grayscale
//            Bitmap t1 = im.Grayscale(ty);
//            createNewFile(t1, u, "gray");
//            //run Segmentation
//            Bitmap t2 = im.Image_Segmentation(ty, (float)((43*Math.PI)/180), (float)0.55 );
//            createNewFile(t2, u, "seg");
//            //run Blur
//            Bitmap t3 = im.Gaussian_Blur(ty,16);
//            createNewFile(t3, u, "blur");
//            //run Noise
//            Bitmap t4 = im.NoiseFilter(ty);
//            createNewFile(t4, u, "noise");
//
//        }
//
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);


        //set up Button
        Button new1 = (Button)findViewById(R.id.buttonUp);
        Button sec = (Button) findViewById(R.id.buttonT);
        final Context con = this;
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);


        new1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                Log.d("This: ", "Returned to the thing");


                try {
                    if ( mHapticView != null)
                        mHapticView.deactivate();
                } catch (HapticServiceAdapterException e) {
                    e.printStackTrace();
                }

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });

        sec.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

//                progressDialog  = new ProgressDialog(con);
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.setMessage(" \"Patience is a virtue\" ~some wise guy/girl");
//                progressDialog.setProgress(0);
//                progressDialog.show();
                try {
                    mHapticView.deactivate();
                } catch (HapticServiceAdapterException e) {
                    e.printStackTrace();
                }

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }

        });
        initHaptics();
    }
    public void initHaptics() {
        try {

            // Get the service adapter
            HapticServiceAdapter serviceAdapter = HapticApplication.getHapticServiceAdapter();

            // Create a haptic view and activate it
            mHapticView = HapticView.create(serviceAdapter);
            mHapticView.activate();

            // Set the orientation of the haptic view
            Display display = ((WindowManager)
                    getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int rotation = display.getRotation();
            HapticView.Orientation orientation =
                    HapticView.getOrientationFromAndroidDisplayRotation(rotation);
            mHapticView.setOrientation(orientation);

            // Retrieve texture data from the bitmap
            Bitmap hapticBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.texture);

            byte[] textureData =
                    HapticTexture.createTextureDataFromBitmap(hapticBitmap);

            // Create a haptic texture with the retrieved texture data
            mHapticTexture = HapticTexture.create(serviceAdapter);
            int textureDataWidth = hapticBitmap.getRowBytes() / 4;
            // 4 channels, i.e., ARGB

            int textureDataHeight = hapticBitmap.getHeight();
            mHapticTexture.setSize(textureDataWidth, textureDataHeight);
            mHapticTexture.setData(textureData);

            // Create a haptic material with the created haptic texture
            mHapticMaterial = HapticMaterial.create(serviceAdapter);
            mHapticMaterial.setTexture(0, mHapticTexture);

            // Create a haptic sprite with the haptic material
            mHapticSprite = HapticSprite.create(serviceAdapter);
            mHapticSprite.setMaterial(mHapticMaterial);

            // Add the haptic sprite to the haptic view
            mHapticView.addSprite(mHapticSprite);


        } catch (Exception e) {
            Log.e(null, e.toString());
        }
    }

    public void changeTexture(Bitmap file) {


        HapticServiceAdapter serviceAdapter = HapticApplication.getHapticServiceAdapter();
        byte[] textureData = HapticTexture.createTextureDataFromBitmap(file);
        try {

            //start creating Texture
            mHapticTexture = HapticTexture.create(serviceAdapter);
            int textWidth = file.getRowBytes() /4;
            int textureDataHeight = file.getHeight();
            mHapticTexture.setSize(textWidth, textureDataHeight);
            mHapticTexture.setData(textureData);
            mHapticMaterial = HapticMaterial.create(serviceAdapter);
            mHapticMaterial.setTexture(0, mHapticTexture);
            mHapticSprite = HapticSprite.create(serviceAdapter);
            mHapticSprite.setMaterial(mHapticMaterial);
            mHapticView.addSprite(mHapticSprite);


            if(Serverhelp.tags != null) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Serverhelp.tags);
                lv.setAdapter(arrayAdapter);
            }

        } catch (HapticServiceAdapterException e) {
            Log.d("Loggin errors: ", e.toString());
        } catch (NativeHapticObjectException e) {
            Log.d("Error(texture): ", e.toString());
        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // The activity is gaining focus
        if (hasFocus) {
            try {

                // Set the size and position of the haptic sprite to correspond to the view we created
                View view = findViewById(R.id.imageView1);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                mHapticSprite.setSize(view.getWidth(), view.getHeight());
                mHapticSprite.setPosition(location[0], location[1]);

            } catch (Exception e) {
                Log.e(null, e.toString());
            }
        }
    }


    static Intent data;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

            //load completely new image and texture
            MainActivity.data = data;
            Uri targetUri = data.getData();
            Bitmap bitmap = null;
            Bitmap p = null;

            try {
                MyTask task = new MyTask(this);
                task.execute(1,2);
                YouTask ts = new YouTask(this);
                ts.execute(1,2);


                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                //get the gray scale;
                ImageHelper im  = new ImageHelper();
                Bitmap fin = im.Grayscale(bitmap);
                //apply the new texture
                updates(targetUri, bitmap);


                //TODO Apply Filters
                ts.get();
                if(Serverhelp.densities != null && Serverhelp.tags != null)
                    fin = Serverhelp.applyFilter(bitmap);
                changeTexture(fin);
                updates(targetUri, p);

                try {
                    if (mHapticView != null)
                        mHapticView.activate();
                } catch (HapticServiceAdapterException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // This will only change the texture of the image
        else if (requestCode == 1 && resultCode == RESULT_OK) {


            //load completely new image and texture
            MainActivity.data = data;
            Uri targetUri = data.getData();
            Bitmap bitmap = null;
            Bitmap p = null;

            try {

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                changeTexture(bitmap);
                updates(targetUri);
                progressDialog.dismiss();

                try {
                    if (mHapticView != null)
                        mHapticView.activate();
                } catch (HapticServiceAdapterException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            updates(targetUri);
        }
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("We are done! :D")
                .setConfirmText("Thanks!")
                .show();
        //progressDialog.dismiss();


    }

    public void createNewFile(Bitmap map, int num, String type) {
        File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Bitmap b= map;

        String fil = "file"+type+"0"+num+".png";
        File file = new File(dir, fil);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();

        } catch (Exception e) {}

    }

    /*
    *   This will update the image and name
    *
    * */
    public void updates(Uri i, Bitmap image) {
        textTargetUri.setText(i.toString());
        if(image != null) {
            targetImage.setImageBitmap(image);

        }


    }
    public void updates(Uri i) {
        textTargetUri.setText(i.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mHapticView.deactivate();
        } catch (Exception e) {
            Log.e(null, e.toString());
        }
    }

    class MyTask extends AsyncTask<Integer, Integer, Integer> {

        Context con;
        Bitmap bit1, bit2;
        Uri uri1;
        MyTask(Context context) {
            super();
            this.con = context;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //send in a server request

            Log.d("Path", data.getData().toString());
            String path =  RealPathUtil.getImagePath(con, data.getData());

            Log.d("Path", path);
            if( !path.equals("/")) {
                Serverhelp.getTags(path);
                Serverhelp.getDensity(path);
            }
            Log.d("TASK", "Ended");
            return 1;
        }

        @Override
        protected void onPreExecute() {
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Patience");
//            pDialog.setCancelable(true);
//            pDialog.show();

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            //pDialog.dismiss();
        }
    }
    class YouTask extends AsyncTask<Integer, Integer, Integer> {

        Context con;
        Bitmap bit1, bit2;
        Uri uri1;
        YouTask(Context context) {
            super();
            this.con = context;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //send in a server request
            Log.d("Path", data.getData().toString());
            String path =  RealPathUtil.getImagePath(con, data.getData());

            Log.d("Path", path);
            //Serverhelp.getTags(path);
            Serverhelp.getDensity(path);
            Log.d("TASK", "Ended");
            return 1;
        }

        @Override
        protected void onPreExecute() {
//            pDialog = new SweetAlertDialog(con, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Patience");
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            pDialog.
//            pDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}


