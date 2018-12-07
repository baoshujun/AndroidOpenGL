package com.xxx.opengl.es.androidopengl.video;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


import com.xxx.opengl.es.androidopengl.utils.DisplayUtil;
import com.xxx.opengl.es.androidopengl.utils.GlUtil;
import com.xxx.opengl.es.androidopengl.utils.LOG;
import com.xxx.opengl.es.androidopengl.video.gles.DirectDrawer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author liyachao 296777513
 * @version 1.0
 * @date 2017/3/1
 */
public class CameraGLSurfaceView extends GLSurfaceView implements Renderer, SurfaceTexture.OnFrameAvailableListener {
    private Context mContext;
    private SurfaceTexture mSurface;
    private int mTextureID = -1;
    private int mBitmapTextureID = -1;
    private DirectDrawer mDirectDrawer;
    private DirectDrawer mBitmapDirectDrawer;

    private TextureResources mTextureResources;

    // 小视频的高度
    private float mThumbnailHeight;
    // 小视频的宽度
    private float mThumbnailWidth;

    // 记录小视频的坐标
    private RectF mThumbnailRect;
    // 屏幕的宽度
    private float mScreenWidth;
    // 屏幕的高度
    private float mScreenHeight;
    //距离屏幕的最小距离
    private int mMargin;
    //最小的滑动距离
    private int mTouchSlop;

    // 标识符，判断手指按下的范围是否在小视频的坐标内
    private boolean mTouchThumbnail = false;

    // 标识符，判断手指是移动小视频而不是点击小视频
    private boolean isMoveThumbnail = false;
    // 按下时手指的x坐标值
    private float mDownX = 0;
    // 按下时手指的y坐标值
    private float mDownY = 0;

    private float mLastYLength = 0;
    private float mLastXLength = 0;

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        // 设置OpenGl ES的版本为2.0
        setEGLContextClientVersion(2);
        // 设置与当前GLSurfaceView绑定的Renderer
        setRenderer(this);
        // 设置渲染的模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        mDirectDrawers = new ArrayList<>();


        mScreenWidth = DisplayUtil.getScreenWidthPixels(mContext);
        mScreenHeight = DisplayUtil.getScreenHeightPixels(mContext);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();//最小的滑动距离
        mThumbnailWidth = mScreenWidth / 4f;
        mThumbnailHeight = mScreenHeight / 4f;

        mMargin = DisplayUtil.dip2px(mContext, 2);
        mThumbnailRect = new RectF(mMargin,
                (mScreenHeight - mMargin), (mMargin + mThumbnailWidth), (mScreenHeight - mMargin - mThumbnailHeight));

        mTextureResources = TextureResources.getInstance();


    }

    private List<DirectDrawer> mDirectDrawers;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // TODO Auto-generated method stub
        LOG.logI("onSurfaceCreated...");
        mTextureID = GlUtil.createTextureID();
        mSurface = new SurfaceTexture(mTextureID);
        mSurface.setOnFrameAvailableListener(this);
        mDirectDrawer = new DirectDrawer(mTextureID);
        mDirectDrawer.setFromCamera(true);
        CameraCapture.get().openBackCamera();

        mBitmapTextureID = GlUtil.loadTexture(mTextureResources.getPicBitmap());
        mBitmapDirectDrawer = new DirectDrawer(mBitmapTextureID);
        mBitmapDirectDrawer.setFromCamera(false);
        mDirectDrawers.add(mDirectDrawer);
        mDirectDrawers.add(mBitmapDirectDrawer);
        LOG.logI("mTextureID: " + mBitmapTextureID);
        LOG.logI("mTextureID: " + mTextureID);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        LOG.logI("onSurfaceChanged...");
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        GLES20.glViewport(0, 0, width, height);
        if (!CameraCapture.get().isPreviewing()) {
            CameraCapture.get().doStartPreview(mSurface);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        LOG.logI("onDrawFrame...");
        // 设置白色为清屏
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        // 清除屏幕和深度缓存
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // 更新纹理
        mSurface.updateTexImage();

        // mDirectDrawers中有两个对象，一个是绘制Camera传递过来的数据，一个是绘制由bitmap转换成的纹理
        for (int i = 0; i < mDirectDrawers.size(); i++) {
            DirectDrawer directDrawer = mDirectDrawers.get(i);
            if (i == 0) {
                directDrawer.resetMatrix();
            } else {
                directDrawer.calculateMatrix(mThumbnailRect, mScreenWidth, mScreenHeight);
            }
            directDrawer.draw();
        }
    }


    /**
     * 移动小视频
     *
     * @param rectF   小视频的坐标
     * @param lengthY 在Y轴移动的距离
     * @param lengthX 在X轴移动的距离
     */
    public void moveView(RectF rectF, float lengthY, float lengthX) {
        rectF.top = rectF.top - (lengthY - mLastYLength);
        rectF.bottom = rectF.bottom - (lengthY - mLastYLength);
        rectF.left = rectF.left + (lengthX - mLastXLength);
        rectF.right = rectF.right + (lengthX - mLastXLength);

        if (rectF.top > (mScreenHeight - mMargin)) {
            rectF.top = mScreenHeight - mMargin;
            rectF.bottom = rectF.top - mThumbnailHeight;
        }

        if (rectF.bottom < mMargin) {
            rectF.bottom = mMargin * 1f;
            rectF.top = rectF.bottom + mThumbnailHeight;
        }

        if (rectF.right > (mScreenWidth - mMargin)) {
            rectF.right = mScreenWidth - mMargin;
            rectF.left = rectF.right - mThumbnailWidth;
        }

        if (rectF.left < mMargin) {
            rectF.left = mMargin;
            rectF.right = rectF.left + mThumbnailWidth;
        }

        mLastYLength = lengthY;
        mLastXLength = lengthX;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                if (mDownX > mThumbnailRect.left && mDownX < mThumbnailRect.right
                        && mDownY > mThumbnailRect.bottom && mDownY < mThumbnailRect.top) {
                    mTouchThumbnail = true;
                    mLastYLength = 0;
                    mLastXLength = 0;
                    return true;
                } else {
                    mTouchThumbnail = false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                if (mTouchThumbnail) {
                    float lengthX = Math.abs(mDownX - moveX);
                    float lengthY = Math.abs(mDownY - moveY);
                    float length = (float) Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthY, 2));
                    if (length > mTouchSlop) {
                        moveView(mThumbnailRect, mDownY - moveY, moveX - mDownX);
                        isMoveThumbnail = true;
                    } else {
                        isMoveThumbnail = false;
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTouchThumbnail) {
                    mLastYLength = 0;
                    mLastXLength = 0;
                    //抬起手指时，如果不是移动小视频，那么就是点击小视频
                    if (!isMoveThumbnail) {
                        changeThumbnailPosition();
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeThumbnailPosition() {
        DirectDrawer directDrawer = mDirectDrawers.remove(mDirectDrawers.size() - 1);
        mDirectDrawers.add(0, directDrawer);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        CameraCapture.get().doStopCamera();
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        // TODO Auto-generated method stub
        LOG.logI("onFrameAvailable...");
        this.requestRender();
    }

    public void switchCamera() {
        CameraCapture.get().switchCamera(1);
        mDirectDrawer.setBackCamera(CameraCapture.get().isOpenBackCamera());
    }

}
