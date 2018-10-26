package codigo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author Glauco
 */
public class Autorama
        implements GLEventListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();

    public static void main(String args[]) {
        new Autorama();
    }
    private double g;

    public Autorama() {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);

        JFrame frame = new JFrame("Exemplo01");
        frame.setSize(500, 500);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });

    }

    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
        GL gl = glAuto.getGL();
        gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    public void display(GLAutoDrawable glAuto) {

        GL2 gl = glAuto.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT
                | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        //glu.gluLookAt(4, 4, 4, 4, 4, -4, 2, -2, -100); implementar camera
        gl.glTranslated(0, 0, -4);
        gl.glRotated(85, 1, 0, 0); // Rotação Camera
        gl.glRotated(90, 0, 1, 0); // Rotação Camera

        desenhaCaixaAutorama(gl);
        gl.glTranslated(-5 + g, 0, 0);

        if (g < 5) {
            g += 0.01;
        } else {
            g = 0;
        }

        desenhaCarro(gl, glut, 0, 0, 1);

        gl.glTranslated(0, 0, 1);

        desenhaCarro(gl, glut, 1, 0, 0);
    }

//    private void desenhaTabuleiro(GL2 gl) {
//        gl.glPushMatrix();
//        for (int i = 0; i < 8; i++) {
//            desenhaLinha(gl, i % 2);
//            gl.glTranslated(0, 2, 0);
//        }
//        gl.glPopMatrix();
//    }
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {

        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(140, 1, 1, 90);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -5);
    }

    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

    }

    public void dispose(GLAutoDrawable glad) {

    }

    public void desenhaCaixaAutorama(GL2 gl) {

        gl.glPushMatrix();
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                //PAREDE DIREITA
                gl.glColor3f(1, 0, 0);
                gl.glVertex3d(-10, -1, 10);//x,y,z
                gl.glVertex3d(-10, 1, 10);
                gl.glVertex3d(10, 1, 10);
                gl.glVertex3d(10, -1, 10);

                //PAREDE ESQUERDA
                gl.glColor3f(0, 1, 0);
                gl.glVertex3d(-10, -1, -10);
                gl.glVertex3d(-10, 1, -10);
                gl.glVertex3d(10, 1, -10);
                gl.glVertex3d(10, -1, -10);

                //PAREDE ATRAS
                gl.glColor3f(0, 0, 1);
                gl.glVertex3d(-10, -1, -10);
                gl.glVertex3d(-10, -1, 10);
                gl.glVertex3d(-10, 1, 10);
                gl.glVertex3d(-10, 1, -10);

                //PAREDE FRENTE
                gl.glColor3f(1, 0, 1);
                gl.glVertex3d(10, -1, -10);
                gl.glVertex3d(10, -1, 10);
                gl.glVertex3d(10, 1, 10);
                gl.glVertex3d(10, 1, -10);

                //CHÃO
                gl.glColor3f(1, 1, 1);
                gl.glVertex3d(-10, -1, -10);
                gl.glVertex3d(10, -1, -10);
                gl.glVertex3d(10, -1, 10);
                gl.glVertex3d(-10, -1, 10);

            }
            gl.glEnd();
        }
        gl.glPopMatrix();

        /*gl.glBegin(GL2.GL_QUADS);
         gl.glColor3f(1,0,0);
         gl.glVertex3d(-1, -1, -1);
         gl.glVertex3d(1, -1, -1);
         gl.glVertex3d(1, -1, 1);
         gl.glVertex3d(-1, -1, 1);
         gl.glEnd();
            
         gl.glBegin(GL2.GL_QUADS);
         gl.glColor3f(0, 1, 0);
         gl.glVertex3d(-1, 1, -1);
         gl.glVertex3d( 1, 1, -1);
         gl.glVertex3d(1, -1, -1);
         gl.glVertex3d(-1, -1, -1);
         gl.glEnd();
            
         gl.glBegin(GL2.GL_QUADS);
         gl.glColor3f(0, 0, 1);
         gl.glVertex3d(-1, 1, 1);
         gl.glVertex3d(1, 1, 1);
         gl.glVertex3d(1, -1, 1);
         gl.glVertex3d(-1, -1, 1);
         gl.glEnd();
            
         gl.glBegin(GL2.GL_QUADS);
         gl.glColor3f(1, 0, 1);
         gl.glVertex3d(1, 1, -1);
         gl.glVertex3d(1, 1, 1);
         gl.glVertex3d(1, -1, 1);
         gl.glVertex3d(1, -1, -1);
         gl.glEnd();
            
         gl.glBegin(GL2.GL_QUADS);
         gl.glColor3f(0, 1, 1);
         gl.glVertex3d(-1, 1, -1);
         gl.glVertex3d(-1, 1, 1);
         gl.glVertex3d(-1, -1, 1);
         gl.glVertex3d(-1, -1, -1);
         gl.glEnd();*/
    }

    public void desenhaCarro(GL2 gl, GLUT glut, double r, double g, double b) {
        gl.glPushMatrix();
        gl.glTranslated(2, 0, 0);
        gl.glColor3d(r, g, b);
        glut.glutSolidCube(0.5f);

        gl.glTranslated(0.5, 0, 0);
        gl.glColor3d(r, g, b);
        glut.glutSolidCube(0.5f);

        gl.glTranslated(0.1, -0.275, -0.17);
        gl.glColor3d(0, 0, 0);
        glut.glutSolidSphere(0.075, 20, 20);

        gl.glTranslated(0, 0, 0.34);
        gl.glColor3d(0, 0, 0);
        glut.glutSolidSphere(0.075, 20, 20);

        gl.glTranslated(-0.625, 0, 0);
        gl.glColor3d(0, 0, 0);
        glut.glutSolidSphere(0.075, 20, 20);

        gl.glTranslated(0, 0, -0.34);
        gl.glColor3d(0, 0, 0);
        glut.glutSolidSphere(0.075, 20, 20);
        gl.glPopMatrix();
    }

//    private void desenhaQuadrado(int cor, GL2 gl) {
//        gl.glColor3f(cor, cor, cor);
//        gl.glBegin(GL2.GL_QUADS);
//        gl.glVertex3d(1, 1, 0);
//        gl.glVertex3d(-1, 1, 0);
//        gl.glVertex3d(-1, -1, 0);
//        gl.glVertex3d(1, -1, 0);
//        gl.glEnd();
//
//    }
//    private void desenhaLinha(GL2 gl, int start) {
//        gl.glPushMatrix();
//        for (int i = 0; i < 8; i++) {
//            if (start == 0) {
//                desenhaQuadrado(i % 2, gl);
//            } else {
//                desenhaQuadrado((i + 1) % 2, gl);
//            }
//            gl.glTranslated(2, 0, 0);
//        }
//        gl.glPopMatrix();
//    }
}
