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


5.glReadPixels函数
  GPU渲染完数据在显存，回传内存的唯一方式glReadPixels函数
  glReadPixels：读取一些像素。当前可以简单理解为“把已经绘制好的像素（它可能已经被保存到显卡的显存中）读取到内存”。
   
  3、glReadPixels的用法和举例
  3.1 函数的参数说明 该函数总共有七个参数。
  前四个参数可以得到一个矩形，该矩形所包括的像素都会被读取出来。
  （第一、二个参数表示了矩形的左下角横、纵坐标，坐标以窗口最左下角为零，最右上角为最大值；第三、四个参数表示了矩形的宽度和高度）
  第五个参数表示读取的内容，例如：GL_RGB就会依次读取像素的红、绿、蓝三种数据，GL_RGBA则会依次读取像素的红、绿、蓝、alpha四种数据，GL_RED则只读取像素的红色数据（类似的还有GL_GREEN，GL_BLUE，以及GL_ALPHA）。如果采用的不是RGBA颜色模式，而是采用颜色索引模式，则也可以使用GL_COLOR_INDEX来读取像素的颜色索引。目前仅需要知道这些，但实际上还可以读取其它内容，例如深度缓冲区的深度数据等。
  第六个参数表示读取的内容保存到内存时所使用的格式，例如：GL_UNSIGNED_BYTE会把各种数据保存为GLubyte，GL_FLOAT会把各种数据保存为GLfloat等。 第七个参数表示一个指针，像素数据被读取后，将被保存到这个指针所表示的地址。注意，需要保证该地址有足够的可以使用的空间，以容纳读取的像素数据。例如一幅大小为256*256的图象，如果读取其RGB数据，且每一数据被保存为GLubyte，总大小就是：256*256*3 = 196608字节，即192千字节。如果是读取RGBA数据，则总大小就是256*256*4 = 262144字节，即256千字节。

  注意：glReadPixels实际上是从缓冲区中读取数据，如果使用了双缓冲区，则默认是从正在显示的缓冲（即前缓冲）中读取，而绘制工作是默认绘制到后缓冲区的。因此，如果需要读取已经绘制好的像素，往往需要先交换前后缓冲。

