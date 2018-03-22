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

public class MyPyramid {
    int count;

    final int numOfObjs = 6;
    final int buffersPerObj = 3;
    final int COORDS_PER_VERTEX = 3;

    enum OBJECTS {OBJECT_TOP, OBJECT_BOTTOM, OBJECT_RIGHT, OBJECT_LEFT, OBJECT_BEHIND, OBJECT_FRONT}

    final float[] VERTEX_COORDS_BOTTOM = {0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
    };

    final float[] VERTEX_COORDS_TOP = {
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f};

    final float[] VERTEX_COORDS_RIGHT = {
            0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f};

    final float[] VERTEX_COORDS_LEFT = {
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f};

    final float[] VERTEX_COORDS_BEHIND = {
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f};

    final float[] VERTEX_COORDS_FRONT = {
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f};

    final float[] VERTEX_COLORS_BOTTOM = {1.0f, 0.0f,0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f,
    };

    final float[] VERTEX_COLORS_BEHIND = {0.0f, 0.0f,1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
    };

    final float[] VERTEX_COLORS_TOP = {1.0f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f};

    final float[] VERTEX_COLORS_RIGHT = {1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f};

    final float[] VERTEX_COLORS_LEFT = {0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f};

    final float[] VERTEX_COLORS_FRONT = {1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f};

    final byte[] ELEMENTS_ARRAY = {0,1,2,0,2,3};

    int vertexCount = 4;//VERTEX_COORDS.length / COORDS_PER_VERTEX;

    float[] lightColor = {0.8f,0.8f,0.8f,1.0f};

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
            "varying vec4 outColor;" +
            "void main(){" +
            "vec4 lightColor=vec4(0.8,0.8,0.8,1.0);" +
            "float ambientStrength=0.8;" +
            "vec4 ambient=ambientStrength*lightColor;" +
            "gl_FragColor=ambient*outColor;" +
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

    public MyPyramid(MyGLSurfaceRenderer renderer, float baseCenterToVertexDistance, float height, int numOfSide){
        this.renderer = renderer;
        int index = 0;

        //GLES30.glDisable(GLES30.GL_CULL_FACE);
        //GLES30.glDisable(GLES30.GL_CULL_FACE_MODE);

        GLES30.glGenVertexArrays(numOfObjs,VAOs,0);
        GLES30.glGenBuffers(numOfObjs*buffersPerObj, vBuffers,0);

        int vertexShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragShader = MyGLSurfaceRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        Log.e("shaders",GLU.gluErrorString(GLES30.glGetError()));
        program = GLES30.glCreateProgram();
        Log.e("createProg",GLU.gluErrorString(GLES30.glGetError()));
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragShader);
        Log.e("attachShader",GLU.gluErrorString(GLES30.glGetError())+","+vertexShader+","+fragShader);
        GLES30.glLinkProgram(program);
        Log.e("linkProg",GLU.gluErrorString(GLES30.glGetError()));
        GLES30.glUseProgram(program);
        Log.e("progInit",GLU.gluErrorString(GLES30.glGetError())+","+program);

        initObject(OBJECTS.OBJECT_TOP.ordinal(), VERTEX_COORDS_TOP, VERTEX_COLORS_TOP, ELEMENTS_ARRAY);
        initObject(OBJECTS.OBJECT_BOTTOM.ordinal(), VERTEX_COORDS_BOTTOM, VERTEX_COLORS_BOTTOM, ELEMENTS_ARRAY);
        initObject(OBJECTS.OBJECT_RIGHT.ordinal(), VERTEX_COORDS_RIGHT, VERTEX_COLORS_RIGHT, ELEMENTS_ARRAY);
        initObject(OBJECTS.OBJECT_LEFT.ordinal(), VERTEX_COORDS_LEFT, VERTEX_COLORS_LEFT, ELEMENTS_ARRAY);
        initObject(OBJECTS.OBJECT_BEHIND.ordinal(), VERTEX_COORDS_BEHIND, VERTEX_COLORS_BEHIND, ELEMENTS_ARRAY);
        initObject(OBJECTS.OBJECT_FRONT.ordinal(), VERTEX_COORDS_FRONT, VERTEX_COLORS_FRONT, ELEMENTS_ARRAY);
    }

    public void initObject(int object, float[] VERTEX_COORDS, float[] VERTEX_COLORS, byte[] ELEMENTS_ARRAY){
        Log.e("b4bindVA","Error:"+GLU.gluErrorString(GLES30.glGetError()));
        GLES30.glBindVertexArray(VAOs[object]);
        Log.e("bindVA","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(VERTEX_COORDS);
        vertexBuffer.position(0);
        Log.e("vertexBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        ByteBuffer byteColorBuffer = ByteBuffer.allocateDirect(VERTEX_COLORS.length * 4);
        byteColorBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer colorBuffer = byteColorBuffer.asFloatBuffer();
        colorBuffer.put(VERTEX_COLORS);
        colorBuffer.position(0);
        Log.e("colorBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        ByteBuffer elementsBuffer = ByteBuffer.allocateDirect(ELEMENTS_ARRAY.length * 4);
        elementsBuffer.order(ByteOrder.nativeOrder());
        //FloatBuffer elementsBuffer = byteElementsBuffer.asFloatBuffer();
        elementsBuffer.put(ELEMENTS_ARRAY);
        elementsBuffer.position(0);
        Log.e("elementsBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[object*buffersPerObj+0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COORDS.length,vertexBuffer,GLES30.GL_STATIC_DRAW);
        int index = GLES30.glGetAttribLocation(program,"position");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,3,GLES30.GL_FLOAT,false,COORDS_PER_VERTEX*4, 0);
        Log.e("vBindBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,vBuffers[object*buffersPerObj+1]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,4*VERTEX_COLORS.length,colorBuffer,GLES30.GL_STATIC_DRAW);
        index = GLES30.glGetAttribLocation(program,"color");
        GLES30.glEnableVertexAttribArray(index);
        GLES30.glVertexAttribPointer(index,4,GLES30.GL_FLOAT,false,4*4,0);
        Log.e("cBindBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, vBuffers[object*buffersPerObj+2]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, 1*ELEMENTS_ARRAY.length, elementsBuffer, GLES30.GL_STATIC_DRAW);
        Log.e("eBindBuff","Error:"+GLU.gluErrorString(GLES30.glGetError()));

        GLES30.glBindVertexArray(0);
        Log.e("Done","Error:"+GLU.gluErrorString(GLES30.glGetError()));
    }

    public void draw(float[] mvpMatrix){
        drawObject(OBJECTS.OBJECT_TOP.ordinal(),mvpMatrix);
        drawObject(OBJECTS.OBJECT_BOTTOM.ordinal(),mvpMatrix);
        drawObject(OBJECTS.OBJECT_RIGHT.ordinal(),mvpMatrix);
        drawObject(OBJECTS.OBJECT_LEFT.ordinal(),mvpMatrix);
        drawObject(OBJECTS.OBJECT_BEHIND.ordinal(),mvpMatrix);
        drawObject(OBJECTS.OBJECT_FRONT.ordinal(),mvpMatrix);
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
