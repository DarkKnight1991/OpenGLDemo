package com.outliers.android.opengltest;

import android.opengl.GLES30;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by nayakasu on 3/7/18.
 */

public class MySquare {
    int count;

    final int COORDS_PER_VERTEX = 3;

    final float[] VERTEX_COORDS = {0.5f, 0.0f, 0.0f,
            0.0f,0.5f, 0.0f,
            -0.5f, 0.0f, 0.0f};

    final float[] VERTEXT_COLORS = {1.0f, 0.0f,0.0f, 1.0f,
            0.0f, 1.0f,0.0f, 1.0f,
            0.0f, 0.0f,1.0f, 1.0f,};

    int vertexCount = VERTEX_COORDS.length / COORDS_PER_VERTEX;

    final String VERTEX_SHADER = "attribute vec3 position;" +
            "uniform mat4 mvpMatrix;" +
            "vec4 color;" +
            "varying vec4 frag_color;" +
            "void main(){" +
            "frag_color=color;" +
            "gl_Position=mvpMatrix*vec4(position,1.0);" +
            "}";
    final String FRAGMENT_SHADER = "precision mediump float;" +
            "varying vec4 frag_color;" +
            "void main(){" +
            "gl_FragColor=frag_color;" +
            "}";


    int coordinatesPerVertex = 3;
    int program;
    FloatBuffer vertexBuffer,colorBuffer;
    MyGLSurfaceRenderer renderer;

    public MySquare(MyGLSurfaceRenderer renderer){
        this.renderer = renderer;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(VERTEX_COORDS);
        vertexBuffer.position(0);

        ByteBuffer byteColorBuffer = ByteBuffer.allocateDirect(VERTEXT_COLORS.length * 4);
        byteColorBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteColorBuffer.asFloatBuffer();
        colorBuffer.put(VERTEX_COORDS);
        colorBuffer.position(0);

        int vertexShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragShader);

        GLES30.glLinkProgram(program);
        GLES30.glGetError();
    }

    public void draw(){
        GLES30.glUseProgram(program);

        int positionHandle = GLES30.glGetAttribLocation(program, "position");
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
        //Log.e("uniformMvp", GLES30.glGetError()+",");

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        //Log.e("drawArr", GLU.gluErrorString(GLES30.glGetError())+","+GLES30.glGetProgramInfoLog(program));
        GLES30.glDisableVertexAttribArray(positionHandle);
        //Log.e("draw", GLES30.glGetError()+",");
    }

    public void glGetErrorType(int errorCode){

    }
}
