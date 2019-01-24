package com.xxx.opengl.es.study.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FirstRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //surface被创建后需要做的处理
        //设置背景颜色
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // 渲染窗口大小发生改变或者屏幕方法发生变化时候回调
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //执行渲染工作
        //重新绘制背景颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

}

