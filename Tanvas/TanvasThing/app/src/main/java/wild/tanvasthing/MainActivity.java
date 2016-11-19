package wild.tanvasthing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.net.URI;

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

    TextView textTargetUri;
    ImageView targetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTargetUri = (TextView)findViewById(R.id.vieww);
        targetImage = (ImageView) findViewById(R.id.view);

//        Bitmap temp = BitmapFactory.decodeResource(getResources(),
//                R.drawable.overlay);
//        ImageHelper imm = new ImageHelper();
//        temp = imm.Image_Segmentation(temp,(float)((43*Math.PI)/180), (float)0.55);
//        targetImage.setImageBitmap(temp);


        //set up Button
        Button new1 = (Button)findViewById(R.id.buttonUp);
        Button sec = (Button) findViewById(R.id.buttonT);
        final Context con = this;

        new1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //
                Log.d("This: ", "Returned to the thing");
                progressDialog  = new ProgressDialog(con);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage(" \"Patience is a virtue\" ~some wise guy/girl");
                progressDialog.setProgress(0);
                progressDialog.show();
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });

        sec.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

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
                View view = findViewById(R.id.view);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                mHapticSprite.setSize(view.getWidth(), view.getHeight());
                mHapticSprite.setPosition(location[0], location[1]);

            } catch (Exception e) {
                Log.e(null, e.toString());
            }
        }
    }

    public void onClick(View arg0) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    static Intent data;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        //load completely new image and texture
        MainActivity.data = data;

     //   MyTask task = new MyTask(this);
   //     task.execute(1,2);

        Uri targetUri = data.getData();
        Bitmap bitmap = null;
        Bitmap p = null;

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

            try {

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                //get the gray scale;
                ImageHelper im  = new ImageHelper();
                Bitmap fin = im.Grayscale(bitmap);
                //apply the new texture
                updates(targetUri, bitmap);
                changeTexture(fin);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }
        // This will only change the texture of the image
        else if (requestCode == 1 && resultCode == RESULT_OK) {

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                changeTexture(bitmap);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            updates(targetUri);
        }
        updates(targetUri, p);
        progressDialog.dismiss();

    }

    /*
    *   This will update the image and name
    *
    * */
    public void updates(Uri i, Bitmap image) {
        textTargetUri.setText(i.toString());
        if(image != null)
            targetImage.setImageBitmap(image);

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

    class MyTask extends AsyncTask<Integer, Integer, Bitmap> {

        Context con;
        Bitmap bit1, bit2;
        Uri uri1;
        MyTask(Context context) {
            super();
            this.con = context;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int requestCode = params[0];
            int resultCode = params[1];
            Bitmap bitmap = null;

            //load completely new image and texture
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                Uri targetUri = data.getData();
                try {

                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    //get the gray scale;
                    ImageHelper im  = new ImageHelper();

                    //apply the new texture
                    bit1 = bitmap;
                    uri1 = targetUri;


                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }
            // This will only change the texture of the image
            else if (requestCode == 1 && resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                try {

                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    changeTexture(bitmap);

                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                }
                updates(targetUri);
            }
            Log.d("TASK", "Ended");
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            return;
        }
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }
}


