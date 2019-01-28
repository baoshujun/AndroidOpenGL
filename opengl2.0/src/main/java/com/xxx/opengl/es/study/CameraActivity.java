package com.xxx.opengl.es.study;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xxx.opengl.es.study.video.CameraGLSurfaceView;

/**
 *  Android OpenGL渲染双视频
 *  https://blog.csdn.net/a296777513/article/details/70495534
 *
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    CameraGLSurfaceView mCameraGLSurfaceView;
    Button mSwitchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);

        mCameraGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.camera_gl_surface_view);

        mSwitchBtn = (Button) findViewById(R.id.switch_camera);
        mSwitchBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mCameraGLSurfaceView.bringToFront();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mCameraGLSurfaceView.onPause();
    }


    @Override
    public void onClick(View v) {
        mCameraGLSurfaceView.switchCamera();
    }
}
