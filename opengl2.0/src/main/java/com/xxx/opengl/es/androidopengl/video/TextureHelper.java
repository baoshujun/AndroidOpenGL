package com.xxx.opengl.es.androidopengl.video;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLUtils.texImage2D;

public class TextureHelper {
    final static int[] textureObjectIds = new int[1];

    public static int loadTexture(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return 0;
        }

        glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            return 0;
        }

        // Bind to the texture in OpenGL
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        // Set filtering: a default must be set, or the texture will be
        // black.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        // Load the bitmap into the bound texture.
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

        return textureObjectIds[0];
    }
}
