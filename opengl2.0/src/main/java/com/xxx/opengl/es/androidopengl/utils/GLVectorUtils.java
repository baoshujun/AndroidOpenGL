package com.xxx.opengl.es.androidopengl.utils;

public class GLVectorUtils {
    /**
     * 两个向量相减
     */
    public static GLKVector3 GLKVector3Subtract(GLKVector3 vectorLeft, GLKVector3 vectorRight) {
        GLKVector3 v = new GLKVector3();
        v.x = vectorLeft.x - vectorRight.x;
        v.y = vectorLeft.y - vectorRight.y;
        v.z = vectorLeft.z - vectorRight.z;
        return v;
    }

    /**
     * 计算向量的长度
     * 计算一个向量的长度，其实就是一个点到点（0，0，0）的距离长度
     */
    public static float GLKVector3Length(GLKVector3 vector) {
        return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }

    /**
     * 计算两个向量之间的距离
     * @param vectorStart
     * @param vectorEnd
     * @return
     */
    public static float GLKVector3Distance(GLKVector3 vectorStart, GLKVector3 vectorEnd) {
        return GLKVector3Length(GLKVector3Subtract(vectorEnd, vectorStart));
    }

    /**
     * 根据基数计算从一个向量的比例，缩放向量
     * @param vector
     * @param value
     * @return
     */
    public static GLKVector3 GLKVector3MultiplyScalar(GLKVector3 vector, float value) {
        GLKVector3 v = new GLKVector3();
        v.x = vector.x * value;
        v.y = vector.y * value;
        v.z = vector.z * value;
        return v;
    }
    /**
     * 根据基数计算两个向量连成直线上的一点
     * 从向量start 向向量end 移动 value 比列
     */
    public static GLKVector3 GLKVector3Lerp(GLKVector3 vectorStart, GLKVector3 vectorEnd, float value) {
        GLKVector3 v = new GLKVector3();
        v.x = vectorStart.x + value * (vectorEnd.x - vectorStart.x);
        v.y = vectorStart.y + value * (vectorEnd.y - vectorStart.y);
        v.z = vectorStart.z + value * (vectorEnd.z - vectorStart.z);

        return v;

    }

    /**
     * 两个向量相加
     * @param x
     * @param y
     * @return
     */
    public static GLKVector3 GLKVector3Add(GLKVector3 x, GLKVector3 y) {
        GLKVector3 v = new GLKVector3();
        v.x = x.x + y.x;
        v.y = x.y + y.y;
        v.z = x.z + y.z;
        return v;
    }


    /**
     *
     * 计算两个向量形成的矢量积，返回的是一个垂直于两个向量之间形成的平面的一个向量
     * 计算两个向量的矢量积
     */
    public static GLKVector3 GLKVector3CrossProduct(GLKVector3 vectorLeft, GLKVector3 vectorRight) {
        GLKVector3 v = new GLKVector3();
        v.x = vectorLeft.y * vectorRight.z - vectorLeft.z * vectorRight.y;
        v.y = vectorLeft.z * vectorRight.x - vectorLeft.x * vectorRight.z;
        v.z = vectorLeft.x * vectorRight.y - vectorLeft.y * vectorRight.x;
        return v;
    }


    /**
     * 向量标准化
     * 矢量标准化把一个向量转换成为（1，1，1）的向量
     *
     * */
    public static GLKVector3 GLKVector3Normalize(GLKVector3 vector)
    {
        float scale = 1.0f / GLKVector3Length(vector);
        GLKVector3 v = new GLKVector3(vector.x * scale, vector.y * scale, vector.z * scale);
        return v;
    }
    /**
     * 将物体坐标转换成世界坐标
     * @param matrixLeft
     * @param vectorRight
     * @return
     */
    public static GLKVector3 GLKMatrix4MultiplyAndProjectVector3(float[] matrixLeft, GLKVector3 vectorRight) {
        GLKVector4 v4 = GLKMatrix4MultiplyVector4(matrixLeft, GLKVector4Make(vectorRight.x, vectorRight.y, vectorRight.z, 1.0f));
        return GLKVector3MultiplyScalar(GLKVector3Make(v4.x, v4.y, v4.z), 1.0f / v4.w);


    }

    public static GLKVector3 GLKVector3Make(float x, float y, float z) {
        GLKVector3 v = new GLKVector3();
        v.x = x;
        v.y = y;
        v.z = z;
        return v;
    }

    public static GLKVector4 GLKMatrix4MultiplyVector4(float[] matrixLeft, GLKVector4 vector) {
        GLKVector4 v4 = new GLKVector4();
        v4.x = matrixLeft[0] * vector.x + matrixLeft[4] * vector.y + matrixLeft[8] * vector.z + matrixLeft[12] * vector.w;
        v4.y = matrixLeft[1] * vector.x + matrixLeft[5] * vector.y + matrixLeft[9] * vector.z + matrixLeft[13] * vector.w;
        v4.z = matrixLeft[2] * vector.x + matrixLeft[6] * vector.y + matrixLeft[10] * vector.z + matrixLeft[14] * vector.w;
        v4.w = matrixLeft[3] * vector.x + matrixLeft[7] * vector.y + matrixLeft[11] * vector.z + matrixLeft[15] * vector.w;
        return v4;
    }

    public static GLKVector4 GLKVector4Make(float x, float y, float z, float w) {
        GLKVector4 v4 = new GLKVector4();
        v4.x = x;
        v4.y = y;
        v4.z = z;
        v4.w = w;
        return v4;
    }

}
