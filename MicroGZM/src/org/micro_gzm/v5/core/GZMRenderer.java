package org.micro_gzm.v5.core;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.micro_gzm.v5.utils.ColorRGB;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class GZMRenderer implements Renderer {
	
	private ColorRGB bckgrdColor = new ColorRGB(0.5f, 0.5f, 0.5f, 1.0f);
	
	private Vector<Model3D> models;
	
	private int tick = 0;

	private float[] mProjMatrix = new float[16];

	private float[] mVMatrix = new float[16];

	private float[] mMVPMatrix = new float[16];
	
	public GZMRenderer() {

		models = new Vector<Model3D>();
	}
	
	public void addModel(Model3D modelIn) {
		
		models.add(modelIn);
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		// Clears the screen.
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
		// Set the camera position (View matrix)
	    Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

	    // Calculate the projection and view transformation
	    Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

//		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		
//		// Counter-clockwise winding.
//		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
//		// Enable face culling.
//		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
//		// What faces to remove with the face culling.
//		gl.glCullFace(GL10.GL_BACK); // OpenGL docs
//
//		// Enabled the vertices buffer for writing and to be used during
//		// rendering.
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
//
//		gl.glLoadIdentity();
//    	gl.glTranslatef(0, 0, -10);
		
		for (Model3D mdl : models) {

//			mdl.setGL(gl);
//			mdl.rotate(tick, 0, 0, 1);
//			mdl.translate(0, 0, FloatMath.sin(tick)/100);
		    // Draw shape
			mdl.draw(mMVPMatrix);
			
			//mdl.draw(gl);			
		}

//		// Disable the vertices buffer.
//		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
//		// Disable face culling.
//		gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
		
		tick++;
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Adjust the viewport based on geometry changes,
        // such as screen rotation
		GLES20.glViewport(0, 0, width, height);

	    float ratio = (float) width / height;

	    // this projection matrix is applied to object coordinates
	    // in the onDrawFrame() method
	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        
//		// Sets the current view port to the new size.
//		gl.glViewport(0, 0, width, height);
//		// Select the projection matrix
//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		// Reset the projection matrix
//		gl.glLoadIdentity();
//
//		// Calculate the aspect ratio of the window
//		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
//		// Select the modelview matrix
//		gl.glMatrixMode(GL10.GL_MODELVIEW);
//		// Reset the modelview matrix
//		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(bckgrdColor.r, bckgrdColor.g, bckgrdColor.b, bckgrdColor.a);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
	}

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
