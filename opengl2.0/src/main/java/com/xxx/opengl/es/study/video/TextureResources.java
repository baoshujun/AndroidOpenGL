package com.xxx.opengl.es.study.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.xxx.opengl.es.study.MyApplication;
import com.xxx.opengl.es.study.R;
import com.xxx.opengl.es.study.utils.DisplayUtil;


/**
 * @author liyachao 296777513
 * @version 1.0
 * @date 2017/3/10
 */

public class TextureResources {

    // 屏幕的宽度
    private float mScreenWidth;
    // 屏幕的高度
    private float mScreenHeight;

    private Bitmap mBitmap;

    private Canvas mCanvas;


    private TextureResources() {

        mScreenWidth = DisplayUtil.getScreenWidthPixels(MyApplication.getContext());
        mScreenHeight = DisplayUtil.getScreenHeightPixels(MyApplication.getContext());

        mBitmap = Bitmap.createBitmap((int) mScreenWidth / 4, (int) mScreenHeight / 4, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);

    }

    private static class TextureResourcesHolder {
        static TextureResources mTextureResources = new TextureResources();
    }

    public static TextureResources getInstance() {
        return TextureResourcesHolder.mTextureResources;
    }

    public Bitmap getPicBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_launcher);
        Matrix matrix = new Matrix();
//        matrix.setRotate(-90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        matrix.preScale(0.25f,0.25f,0.5f,0.5f);
        mCanvas.drawColor(Color.WHITE);
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        int halfWidth = (mBitmap.getWidth() - bitmap.getWidth())/2;
        int halfHeight = (mBitmap.getHeight() - bitmap.getHeight())/2;
        Rect dst = new Rect(halfWidth,halfHeight,halfWidth+bitmap.getWidth(),halfHeight+bitmap.getHeight());
        mCanvas.drawBitmap(bitmap,src,dst,null);
        bitmap.recycle();
        return mBitmap;
    }

}
