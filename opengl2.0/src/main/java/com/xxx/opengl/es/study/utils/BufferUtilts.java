package com.xxx.opengl.es.study.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtilts {
    /**
     *
     * float 32 位，4个 byte
     *
     */
    private static final int BYTES_PER_FLOAT = 4;

    public static FloatBuffer createFloatBuffer(float[] rectangle) {

        FloatBuffer vertexData = ByteBuffer.allocateDirect(rectangle.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(rectangle);
        return vertexData;
    }
}
