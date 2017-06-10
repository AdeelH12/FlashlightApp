package com.example.android.flashlightdemo;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {

     ImageButton imageButton;
     Camera camera;
     boolean isFlash =false;
     boolean isOn = false;
     Camera.Parameters parameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton) findViewById(R.id.btnSwitch);

        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {

            camera = camera.open();
            parameters = camera.getParameters();
            isFlash = true;


        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFlash)
                {

                    //The code below is running a check to see if the flashlight is not on...
                    if(!isOn){

                        imageButton.setImageResource(R.drawable.switchon);

                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();

                        isOn = true;




                    }

                    //Otherwise if the flashlight is on... Do the following...
                    else{

                        imageButton.setImageResource(R.drawable.switchoff);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();

                        isOn  =false;
                    }

                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Flashlight Is Not Availabe On This Device");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            finish();

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        if(camera != null){


            camera.release();
            camera = null;

        }
    }
}


