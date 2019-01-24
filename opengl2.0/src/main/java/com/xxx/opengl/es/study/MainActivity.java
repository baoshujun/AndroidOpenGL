package com.xxx.opengl.es.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClicked(View view){
        switch (view.getId()) {
            case R.id.cameraPreview:
                toActivity(CameraActivity.class);
                break;
            case R.id.firstButton:
                toActivity(FirstActivity.class);
                break;
        }
    }


    private void toActivity(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
