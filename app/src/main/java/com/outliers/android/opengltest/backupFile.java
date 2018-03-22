/*
package com.outliers.android.opengltest;

import android.opengl.GLES30;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

*/
/**
 * Created by nayakasu on 3/6/18.
 *//*


public class MyTriangle {
    int count;

    final int numOfObjs = 1;
    final int buffersPerObj = 2;
    final int COORDS_PER_VERTEX = 3;

    final float[] VERTEX_COORDS = {0.5f,-0.5f, 0.0f,
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f};

    final float[] VERTEX_COLORS = {1.0f, 0.0f,0.0f, 1.0f,
            0.0f, 1.0f,0.0f, 1.0f,
            0.0f, 0.0f,1.0f, 1.0f,};

    int vertexCount = VERTEX_COORDS.length / COORDS_PER_VERTEX;

    final String VERTEX_SHADER ="precision mediump float;" +
            "attribute vec3 position;" +
            "attribute vec4 color;" +
            "uniform mat4 mvpMatrix;" +
            "varying vec4 outColor;"+
            //"varying vec4 frag_color;" +
            //"varying float pos;" +
            "void main(){" +
            //"frag_color=color;" +
            //"pos=position.x+position.y;" +
            "gl_Position=mvpMatrix*vec4(position,1.0);" +
            "outColor=color;"+
            //"gl_Position=vec4(position,1.0);"+
            "}";

    final String FRAGMENT_SHADER = "precision mediump float;" +
            //"varying vec4 frag_color;" +
            //"varying float pos;" +
            "varying vec4 outColor;"+
            "void main(){" +
            "gl_FragColor=outColor;" +
            //"gl_FragColor=vec4(1.0,1.0,1.0,1.0);"+
            "}";


    int coordinatesPerVertex = 3;
    int program;
    int VAOs[] = new int[numOfObjs];
    IntBuffer VAOsBuff;
    int vBuffers[] = new int[numOfObjs * 2];

    FloatBuffer vertexBuffer,colorBuffer;
    MyGLSurfaceRenderer renderer;

    public MyTriangle(MyGLSurfaceRenderer renderer){
        this.renderer = renderer;
        GLES30.glGenVertexArrays(1,VAOs,0);
        GLES30.glGenBuffers(2,vBuffers,0);

        GLES30.glBindVertexArray(VAOs[0]);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(VERTEX_COORDS);
        vertexBuffer.position(0);

        int index = 0;

        ByteBuffer byteColorBuffer = ByteBuffer.allocateDirect(VERTEX_COLORS.length * 4);
        byteColorBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteColorBuffer.asFloatBuffer();
        colorBuffer.put(VERTEX_COLORS);
        colorBuffer.position(0);

        int vertexShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragShader);
        GLES30.glLinkProgram(program);
        GLES30.glUseProgram(program);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COORDS.length,vertexBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"position");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,3,GLES30.GL_FLOAT,false,3*4,0);
        Log.e("posEnable","index="+index+","+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COLORS.length,colorBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"color");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,4,GLES30.GL_FLOAT,false,4*4,0);
        Log.e("colorEnable", "index="+index+","+GLU.gluErrorString(GLES30.glGetError()));

        Log.e("pos","pos:"+GLES30.glGetAttribLocation(program,"position")+","+GLES30.glGetUniformLocation(program,"mvpMatrix"));
        GLES30.glBindVertexArray(0);
        Log.e("End",GLES30.glGetError()+",");
    }

    public void draw(){
        //GLES30.glUseProgram(program);

        */
/*int positionHandle = GLES30.glGetAttribLocation(program, "position");
        //Log.e("posHandle", GLES30.glGetError()+",");
        GLES30.glEnableVertexAttribArray(positionHandle);
        //Log.e("EnableAttrib", GLES30.glGetError()+",");
        GLES30.glVertexAttribPointer(positionHandle,COORDS_PER_VERTEX, GLES30.GL_FLOAT,false,4 * 3, vertexBuffer);
        //Log.e("AttribPtr", GLES30.glGetError()+",");
        int colorHandle = GLES30.glGetUniformLocation(program,"color");
        Log.e("colorHandle", GLES30.glGetError()+",");
        GLES30.glUniform4fv(colorHandle,1,colorBuffer);
        //Log.e("uniformColor", GLES30.glGetError()+",");
        Log.e("color", GLU.gluErrorString(GLES30.glGetError())+","+GLES30.glGetProgramInfoLog(program));
        int mvpHandle = GLES30.glGetUniformLocation(program, "mvpMatrix");
        //Log.e("mvpHandler", GLES30.glGetError()+",");
        GLES30.glUniformMatrix4fv(mvpHandle,1,false,renderer.mvpMatrix,0);
        //Log.e("uniformMvp", GLES30.glGetError()+",");*//*


        GLES30.glBindVertexArray(VAOs[0]);
        int index = GLES30.glGetUniformLocation(program,"mvpMatrix");
        GLES30.glUniformMatrix4fv(index,1,false,renderer.mvpMatrix,0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        Log.e("drawArr", GLU.gluErrorString(GLES30.glGetError())+","+GLES30.glGetProgramInfoLog(program));
        //GLES30.glDisableVertexAttribArray(positionHandle);
        //Log.e("draw", GLES30.glGetError()+",");
        GLES30.glBindVertexArray(0);
    }

    public void glGetErrorType(int errorCode){

    }
}
*/


//region 1 squares, translation,rotation
/*
package com.outliers.android.opengltest;

import android.opengl.GLES30;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

*/
/**
 * Created by nayakasu on 3/6/18.
 *//*


public class MyTwoSquares {
    int count;

    final int numOfObjs = 1;
    final int buffersPerObj = 2;
    final int COORDS_PER_VERTEX = 3;

    final float[] VERTEX_COORDS = {0.5f,-0.5f,0.0f,
            0.5f,0.5f,0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f};

    final float[] VERTEX_COLORS = {1.0f, 0.0f,0.0f, 1.0f,
            0.0f, 1.0f,0.0f, 1.0f,
            0.0f, 0.0f,1.0f, 1.0f,
            0.5f,0.5f,0.5f,0.5f};

    final byte[] ELEMENTS_ARRAY = {0,1,2,0,2,3};

    int vertexCount = VERTEX_COORDS.length / COORDS_PER_VERTEX;

    final String VERTEX_SHADER ="precision mediump float;" +
            "attribute vec3 position;" +
            "attribute vec4 color;" +
            "uniform mat4 mvpMatrix;" +
            "varying vec4 outColor;"+
            //"varying vec4 frag_color;" +
            //"varying float pos;" +
            "void main(){" +
            //"frag_color=color;" +
            //"pos=position.x+position.y;" +
            "gl_Position=mvpMatrix*vec4(position,1.0);" +
            "outColor=color;"+
            //"gl_Position=vec4(position,1.0);"+
            "}";

    final String FRAGMENT_SHADER = "precision mediump float;" +
            //"varying vec4 frag_color;" +
            //"varying float pos;" +
            "varying vec4 outColor;"+
            "void main(){" +
            "gl_FragColor=outColor;" +
            //"gl_FragColor=vec4(1.0,1.0,1.0,1.0);"+
            "}";


    int coordinatesPerVertex = 3;
    int program;
    int VAOs[] = new int[numOfObjs];
    IntBuffer VAOsBuff;
    int vBuffers[] = new int[numOfObjs * 3];

    FloatBuffer vertexBuffer,colorBuffer;
    ByteBuffer elementsBuffer;
    MyGLSurfaceRenderer renderer;

    public MyTwoSquares(MyGLSurfaceRenderer renderer){
        this.renderer = renderer;
        int index = 0;

        //GLES30.glDisable(GLES30.GL_CULL_FACE);
        //GLES30.glDisable(GLES30.GL_CULL_FACE_MODE);

        GLES30.glGenVertexArrays(1,VAOs,0);
        GLES30.glGenBuffers(3,vBuffers,0);

        GLES30.glBindVertexArray(VAOs[0]);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(VERTEX_COORDS);
        vertexBuffer.position(0);

        ByteBuffer byteColorBuffer = ByteBuffer.allocateDirect(VERTEX_COLORS.length * 4);
        byteColorBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteColorBuffer.asFloatBuffer();
        colorBuffer.put(VERTEX_COLORS);
        colorBuffer.position(0);

        elementsBuffer = ByteBuffer.allocateDirect(ELEMENTS_ARRAY.length * 4);
        elementsBuffer.order(ByteOrder.nativeOrder());
        //FloatBuffer elementsBuffer = byteElementsBuffer.asFloatBuffer();
        elementsBuffer.put(ELEMENTS_ARRAY);
        elementsBuffer.position(0);

        int vertexShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragShader);
        GLES30.glLinkProgram(program);
        GLES30.glUseProgram(program);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COORDS.length,vertexBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"position");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,3,GLES30.GL_FLOAT,false,COORDS_PER_VERTEX*4,0);
        Log.e("posEnable","index="+index+","+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COLORS.length,colorBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"color");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,4,GLES30.GL_FLOAT,false,4*4,0);
        Log.e("colorEnable", "index="+index+","+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vBuffers[2]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, 1*ELEMENTS_ARRAY.length, elementsBuffer, GLES30.GL_STATIC_DRAW);

        Log.e("pos","pos:"+GLES30.glGetAttribLocation(program,"position")+","+GLES30.glGetUniformLocation(program,"mvpMatrix"));
        GLES30.glBindVertexArray(0);
        Log.e("End",GLES30.glGetError()+",");
    }

    public void draw(float[] mvpMatrix){

        GLES30.glBindVertexArray(VAOs[0]);
        int index = GLES30.glGetUniformLocation(program,"mvpMatrix");
        GLES30.glUniformMatrix4fv(index,1,false, mvpMatrix,0);
        //GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, ELEMENTS_ARRAY.length, GLES30.GL_UNSIGNED_BYTE,0);
        //Log.e("drawArr", GLU.gluErrorString(GLES30.glGetError())+","+GLES30.glGetProgramInfoLog(program));
        //GLES30.glDisableVertexAttribArray(positionHandle);
        //Log.e("draw", GLES30.glGetError()+",");
        GLES30.glBindVertexArray(0);
    }

    public void glGetErrorType(int errorCode){

    }
}
*/
//endregion

