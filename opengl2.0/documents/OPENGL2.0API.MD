0.调用glUseProgram(program);

  告诉OpenGL在绘制任何东西到屏幕上的时候要使用这个定义的程序。

1.调用glGetAttribLocation(）获取属性的位置

    this.aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

2.有了这个位置，就能告诉OpenGL到哪里去找到这个属性对应的数据了

    this.vertexData.position(0);
    GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, this.vertexData);

   aPositionLocation：这个是属性位置。

   POSITION_COMPONENT_COUNT：这个属性有多少分量(x,y)或者(x,y,z)的个数

   GLES20.GL_FLOAT：这是数据的类型

   false：只有使用整型数组时候，这个数据才有意义。

   stride：多于一个属性时候，就要告诉取下个数据要跳过多少分量。分量个数 * 分量字节数

   vertexData：这个参数告诉OpenGL去哪里读取数据
3.打开这个属性的状态

    GLES20.glEnableVertexAttribArray(aColorLocation);

4.绘制图元

    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
    GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);



