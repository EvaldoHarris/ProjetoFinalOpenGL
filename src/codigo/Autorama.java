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
    
    private double gX, gY, gZ;
    private double gR = 0;
    private boolean reta1=true, reta2=false, reta3=false, reta4=false;
    private int passou = 0;

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
        gl.glTranslated(0, 0, -4);
        gl.glRotated(85, 1, 0, 0); // Rotação Camera
        gl.glRotated(90, 0, 1, 0); // Rotação Camera
        
        desenhaCaixaAutorama(gl);
        
        desenhaPista(gl);
        
        
        gl.glTranslated(-3 + gX, gY, gZ);
        gl.glRotated(gR, 0, 1, 0);

        if(reta1 == true)
        {
            if (gX < 8) {
                gX += 0.02;
                gY = 0;
                gZ = 0;  
            } 
            else {
                gX = 8;
                if(gZ <1)
                {
                    gZ += 0.005;
                    gR -= 0.1;
                }
                else if(gZ >= 1 && gZ < 2)
                {
                    gZ += 0.1;
                    gR -= 0.2;   
                }
                if(gR > -90 && gZ >= 2)
                {
                    reta1 = false;
                    reta2 = true;
                }
            }
        }
        
        if(reta2 == true)
        {
            if(gR > -90 && gZ >= 2 && passou == 0)
            {
                gl.glTranslated(-7, 0, -3);
                gl.glRotated(-90-gR,0,1,0);
                gl.glRotated(0,0,1,0);
            }
            
            if(gZ < 11.3 && passou == 0)
                gZ += 0.02;
            else if(gZ >= 11.3 && gZ <= 11.32 && passou == 0)
            {
                gR -= 90;
                gX -= 6;
                gZ -= 7.5;
                passou = 1;
            }
            else if(gZ >= 2.5 && gZ <= 3.9)
            {
                gZ -= 0.0008;
                gR -= 0.02;
            }
            
            if(gR > -270 && gZ <= 2.5 && passou == 1)
            {
                reta2 = false;
                reta3 = true;
                passou = 0;
            }
        }
        
        if(reta3 == true)
        {
           if(gR > -270 && gZ <= 2.5 && passou == 0)
           {
               gl.glTranslated(-4, 0, 0);
               gl.glRotated(-180-gR, 0, 1, 0);
           }
           
           if(gX > -7 && passou == 0)
               gX -= 0.02;
           else if(gX <= -7 && gX >= -7.02 && passou == 0)
           {
               gR -= 90;
               gX += 8;
               passou = 1;
           }
           else if(gX >= 0.9 && gX <= 1.8 && passou == 1)
           {
               gX += 0.0005;
               gR -= 0.02;
           }
           
           if(gR > -270 && gX >= 1.8 && passou == 1)
           {
               reta3 = false;
               reta4 = true;
               passou = 0;
           }
        }
        
        if(reta4 == true)
        {
            if(gR > -270 && gX >= 1.8 && passou == 0)
            {
               gl.glTranslated(-0.25, 0, 0.4);
               gl.glRotated(90-gR, 0, 1, 0);
            }
            
            if(gZ > -7 && passou == 0)
                gZ -= 0.02;
            else if(gZ <= -7 && gZ >= -7.02 && passou == 0)
            {
                gR -= 60;
                gX -= 1.8;
                gZ += 7;
                passou = 1;
            }
            else if(gZ > -0.5 && passou == 1)
            {
                gZ -= 0.0005;
                gR -= 0.02;
            }
            
            if(gZ <= -0.5 && passou == 1)
            {
                //gR = gX = gY = gZ = 0;
                //gl.glTranslated(5, 0, 0);
                //gl.glRotated(gR, 0, 1, 0);
                reta4 = false;
                reta1 = true;
            }
            
            //TODO: resetar o caminho ou colocar algo para que ele refaça a volta
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
                gl.glColor3f(1, 1, 0);
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
        gl.glTranslated(-2, 0, -7);
        gl.glColor3d(r, g, b);
        glut.glutSolidCube(0.5f);

        gl.glTranslated(0.5, 0, 0);
        gl.glColor3d(r, g, b);
        glut.glutSolidCube(0.5f);

        gl.glTranslated(0.1, -0.273, -0.17);
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
    
    public void desenhaPista(GL2 gl)
    {
        //Retas
        gl.glPushMatrix();
            gl.glPushMatrix();
                gl.glTranslated(-7.5, 0, -7.5);

                gl.glBegin(GL2.GL_QUADS);
                    gl.glColor3f(0, 0, 0);
                    gl.glVertex3d(0.5, -0.999, -1.5);
                    gl.glVertex3d(12.5, -0.999, -1.5);
                    gl.glVertex3d(12.5, -0.999, 1.5);
                    gl.glVertex3d(0.5, -0.999, 1.5);
                gl.glEnd();

                gl.glTranslated(0, 0, 15);

                gl.glBegin(GL2.GL_QUADS);
                    gl.glColor3f(0, 0, 0);
                    gl.glVertex3d(0.5, -0.999, -1.5);
                    gl.glVertex3d(12.5, -0.999, -1.5);
                    gl.glVertex3d(12.5, -0.999, 1.5);
                    gl.glVertex3d(0.5, -0.999, 1.5);
                gl.glEnd();

                gl.glTranslated(13, 0, -7.5);

                gl.glBegin(GL2.GL_QUADS);
                    gl.glColor3f(0, 0, 0);
                    gl.glVertex3d(0.5, -0.999, -5.5);
                    gl.glVertex3d(3.25, -0.999, -5.5);
                    gl.glVertex3d(3.25, -0.999, 5.5);
                    gl.glVertex3d(0.5, -0.999, 5.5);
                gl.glEnd();

                gl.glTranslated(-15.5, 0, 0);

                gl.glBegin(GL2.GL_QUADS);
                    gl.glColor3f(0, 0, 0);
                    gl.glVertex3d(0.5, -0.999, -5.5);
                    gl.glVertex3d(2.5, -0.999, -5.5);
                    gl.glVertex3d(2.5, -0.999, 5.5);
                    gl.glVertex3d(0.5, -0.999, 5.5);
                gl.glEnd();

            gl.glPopMatrix();

            desenhaCurva(gl);
        gl.glPopMatrix();
        
        
    }
    
    public void desenhaCurva(GL2 gl)
    {
        gl.glPushMatrix();
            gl.glTranslated(4.5, 0, -7.5); 
            gl.glRotated(-205, 0, 1, 0);
            gl.glTranslated(-3.7, 0, 0.35);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-176, 0, 1, 0);
            gl.glTranslated(-4.9, 0, -0.075);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(353, 0, 1, 0);
            gl.glTranslated(1.8, 0, -0.4);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-60, 0, 1, 0);
            gl.glTranslated(0.2, 0, -2);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(-0.1, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
        gl.glPopMatrix();
        
        gl.glTranslated(3.8,0,-2);
        gl.glRotated(235, 0, 1, 0);
        
        gl.glPushMatrix();
            gl.glTranslated(4.5, 0, -7.5); 
            gl.glRotated(-205, 0, 1, 0);
            gl.glTranslated(-3.7, 0, 0.35);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-190, 0, 1, 0);
            gl.glTranslated(-3.17, 0, 0.4);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-347, 0, 1, 0);
            gl.glTranslated(-1.75, 0, 0.35);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-320, 0, 1, 0);
            gl.glTranslated(-1.35, 0, 1.15);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd(); 
        gl.glPopMatrix();
        
        gl.glTranslated(3.8,0,-2);
        gl.glRotated(235, 0, 1, 0);
        
        gl.glPushMatrix();
            gl.glTranslated(4.5, 0, -7.5); 
            gl.glRotated(-255, 0, 1, 0);
            gl.glTranslated(-3.7, 0, 7.75);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-189, 0, 1, 0);
            gl.glTranslated(-3.2, 0, 0.3);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3.5, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(145, 0, 1, 0);
            gl.glTranslated(-2.1, 0, 0.5);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(-0.1, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(0, 0, 1, 0);
            gl.glTranslated(-2, 0, -4);
            
            gl.glBegin(GL2.GL_QUADS);
                gl.glColor3f(1, 0, 0);
                gl.glVertex3d(-0.1, -0.998, -1.5);
                gl.glVertex3d(1.25, -0.998, -1.5);
                gl.glVertex3d(1.25, -0.998, 1.5);
            gl.glEnd();
        gl.glPopMatrix();
        
        gl.glTranslated(3.8,0,-2);
        gl.glRotated(235, 0, 1, 0);
        
        gl.glPushMatrix();
            gl.glTranslated(4.5, 0, -7.5); 
            gl.glRotated(330, 0, 1, 0);
            gl.glTranslated(-3, 0, 19.85);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(205, 0, 1, 0);
            gl.glTranslated(-5, 0, -1.15);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(3, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(-30, 0, 1, 0);
            gl.glTranslated(0.6, 0, -1.25);
            
            gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(1, -0.998, -1.5);
                gl.glVertex3d(2.15, -0.998, -1.5);
                gl.glVertex3d(2, -0.998, 1.5);
            gl.glEnd();
            
            gl.glRotated(51, 0, 1, 0);
            gl.glTranslated(0.2,0,1.9);
            
            gl.glBegin(GL2.GL_QUADS);
                gl.glColor3f(0, 0, 0);
                gl.glVertex3d(0.4, -0.998, -1.15);
                gl.glVertex3d(2.5, -0.998, -1.15);
                gl.glVertex3d(2.5, -0.998, 1.15);
                gl.glVertex3d(0.4, -0.998, 1.15);
            gl.glEnd();
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
