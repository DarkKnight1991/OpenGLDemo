package com.outliers.android.opengltest;

import android.opengl.GLES30;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by nayakasu on 3/6/18.
 */

public class MyTwoSquares {
    int count;

    final int numOfObjs = 2;
    final int buffersPerObj = 3;
    final int COORDS_PER_VERTEX = 3;

    final float[] VERTEX_COORDS = {0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            };

    final float[] VERTEX_COORDS1 = {
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f};

    final float[] VERTEX_COLORS = {1.0f, 0.0f,0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
            };

    final float[] VERTEX_COLORS1 = {1.0f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f};

    final byte[] ELEMENTS_ARRAY = {0,1,2,0,2,3};

    int vertexCount = 4;//VERTEX_COORDS.length / COORDS_PER_VERTEX;

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
    int vBuffers[] = new int[numOfObjs * buffersPerObj];

    FloatBuffer vertexBuffer,colorBuffer;
    ByteBuffer elementsBuffer;
    MyGLSurfaceRenderer renderer;

    public MyTwoSquares(MyGLSurfaceRenderer renderer){
        this.renderer = renderer;
        int index = 0;

        //GLES30.glDisable(GLES30.GL_CULL_FACE);
        //GLES30.glDisable(GLES30.GL_CULL_FACE_MODE);

        GLES30.glGenVertexArrays(numOfObjs,VAOs,0);
        GLES30.glGenBuffers(numOfObjs*buffersPerObj, vBuffers,0);

        int vertexShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragShader);
        GLES30.glLinkProgram(program);
        GLES30.glUseProgram(program);

        initObject(0,VERTEX_COORDS,VERTEX_COLORS, ELEMENTS_ARRAY);
        initObject(1,VERTEX_COORDS1,VERTEX_COLORS1, ELEMENTS_ARRAY);
    }

    public void initObject(int object, float[] VERTEX_COORDS, float[] VERTEX_COLORS, byte[] ELEMENTS_ARRAY){

        GLES30.glBindVertexArray(VAOs[object]);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(VERTEX_COORDS);
        vertexBuffer.position(0);

        ByteBuffer byteColorBuffer = ByteBuffer.allocateDirect(VERTEX_COLORS.length * 4);
        byteColorBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer colorBuffer = byteColorBuffer.asFloatBuffer();
        colorBuffer.put(VERTEX_COLORS);
        colorBuffer.position(0);

        ByteBuffer elementsBuffer = ByteBuffer.allocateDirect(ELEMENTS_ARRAY.length * 4);
        elementsBuffer.order(ByteOrder.nativeOrder());
        //FloatBuffer elementsBuffer = byteElementsBuffer.asFloatBuffer();
        elementsBuffer.put(ELEMENTS_ARRAY);
        elementsBuffer.position(0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[object*buffersPerObj+0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COORDS.length,vertexBuffer,GLES30.GL_STATIC_DRAW);
        int index = GLES30.glGetAttribLocation(program,"position");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,3,GLES30.GL_FLOAT,false,COORDS_PER_VERTEX*4, 0);
        Log.e("posEnable","index="+index+","+GLU.gluErrorString(GLES30.glGetError())+","+VERTEX_COORDS[object*vertexCount]+","+VERTEX_COORDS[object*vertexCount+1]+","+VERTEX_COORDS[object*vertexCount+2]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[object*buffersPerObj+1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COLORS.length,colorBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"color");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,4,GLES30.GL_FLOAT,false,4*4,0);
        Log.e("colorEnable", "index="+index+","+GLU.gluErrorString(GLES30.glGetError())+","+VERTEX_COLORS[object*vertexCount]+","+VERTEX_COLORS[object*vertexCount+1]+","+VERTEX_COLORS[object*vertexCount+2]);

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vBuffers[object*buffersPerObj+2]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, 1*ELEMENTS_ARRAY.length, elementsBuffer, GLES30.GL_STATIC_DRAW);

        GLES30.glBindVertexArray(0);
        Log.e("pos","pos:"+GLES30.glGetAttribLocation(program,"position")+","+GLES30.glGetUniformLocation(program,"mvpMatrix"));
    }

    public void draw(float[] mvpMatrix){
        /*GLES30.glBindVertexArray(VAOs[0]);
        int index = GLES30.glGetUniformLocation(program,"mvpMatrix");
        GLES30.glUniformMatrix4fv(index,1,false, mvpMatrix,0);
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, ELEMENTS_ARRAY.length, GLES30.GL_UNSIGNED_BYTE,0);
        GLES30.glBindVertexArray(0);*/
        drawObject(0,mvpMatrix);
        drawObject(1,mvpMatrix);
    }

    public void drawObject(int object, float[] mvpMatrix){
        GLES30.glBindVertexArray(VAOs[object]);
        int index = GLES30.glGetUniformLocation(program,"mvpMatrix");
        GLES30.glUniformMatrix4fv(index,1,false, mvpMatrix,0);
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, ELEMENTS_ARRAY.length, GLES30.GL_UNSIGNED_BYTE,0);
        GLES30.glBindVertexArray(0);
    }

    public void glGetErrorType(int errorCode){

    }
}
