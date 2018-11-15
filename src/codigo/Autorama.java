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
import java.util.Random;

/**
 *
 * @author Glauco
 */
public class Autorama
        implements GLEventListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    
    private Carro cBlue = new Carro(); 
    private Carro cRed = new Carro();

    public static void main(String args[]) {
        new Autorama();
    }

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
        GL2 gl = glAuto.getGL().getGL2();    
        gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
        gl.glEnable(GL.GL_DEPTH_TEST);
           int x = -23, y = 0, z = 8;
                 int tipo = 1;
        float posicaoLuz[] = {x, y, z, tipo};
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void display(GLAutoDrawable glAuto) {
        
        GL2 gl = glAuto.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT
                | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        
     
        //Acertar a posicao da luz
        
        gl.glTranslated(0, 0, -4);
        gl.glRotated(85, 1, 0, 0); // Rotação Camera
        gl.glRotated(90, 0, 1, 0); // Rotação Camera
        
        desenhaCaixaAutorama(gl);
        
        desenhaPista(gl);
        
        //criar método mover carro, adicionar push e pop neles e fazer um diferente para cada carro
        gl.glPushMatrix();
            percurso(gl,cBlue);
            desenhaCarro(gl, glut, 0, 0, 1);
        gl.glPopMatrix();
            
        
        
        gl.glPushMatrix();
            if(cRed.reta1 == true)
                gl.glTranslated(0, 0, 1);
            if(cRed.reta2 == true)
                gl.glTranslated(-1, 0, 0);
            if(cRed.reta3 == true)
                gl.glTranslated(0, 0, -1);
            if(cRed.reta4 == true)
                gl.glTranslated(1, 0, 0);
            percurso(gl,cRed);
            desenhaCarro(gl, glut, 1, 0, 0);
        gl.glPopMatrix();
    }

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

    public void percurso(GL2 gl, Carro c)
    {
        Random rand = new Random();
        int randomValue = rand.nextInt(9 + 1) + 1;
        gl.glTranslated(-3 + c.gX, c.gY, c.gZ);
        gl.glRotated(c.gR, 0, 1, 0);
        
        if(c.reta1 == true)
        {
            if (c.gX < 8) {
                c.gX += 0.004 * randomValue;
                c.gY = 0;
                c.gZ = 0;  
            } 
            else {
                c.gX = 8;
                if(c.gZ <1)
                {
                    c.gZ += 0.005;
                    c.gR -= 0.1;
                }
                else if(c.gZ >= 1 && c.gZ < 2)
                {
                    c.gZ += 0.1;
                    c.gR -= 0.2;   
                }
                if(c.gR > -90 && c.gZ >= 2)
                {
                    c.reta1 = false;
                    c.reta2 = true;
                }
            }
        }
        
        if(c.reta2 == true)
        {
            if(c.gR > -90 && c.gZ >= 2 && c.passou == 0)
            {
                gl.glTranslated(-7, 0, -3);
                gl.glRotated(-90-c.gR,0,1,0);
                gl.glRotated(0,0,1,0);
            }
            
            if(c.gZ < 11.3 && c.passou == 0)
                c.gZ += 0.004 * randomValue;
            else if(c.gZ >= 11.3 && c.gZ <= 11.4 && c.passou == 0)
            {
                c.gR -= 90;
                c.gX -= 6;
                c.gZ -= 7.5;
                c.passou = 1;
            }
            else if(c.gZ >= 2.5 && c.gZ <= 3.9)
            {
                c.gZ -= 0.008;
                c.gR -= 0.2;
            }
            
            if(c.gR > -270 && c.gZ <= 2.5 && c.passou == 1)
            {
                c.reta2 = false;
                c.reta3 = true;
                c.passou = 0;
            }
        }
        
        if(c.reta3 == true)
        {
           if(c.gR > -270 && c.gZ <= 2.5 && c.passou == 0)
           {
               gl.glTranslated(-4, 0, 0);
               gl.glRotated(-180-c.gR, 0, 1, 0);
           }
           
           if(c.gX > -7 && c.passou == 0)
               c.gX -= 0.004 * randomValue;
           else if(c.gX <= -7 && c.gX >= -7.1 && c.passou == 0)
           {
               c.gR -= 90;
               c.gX += 8;
               c.passou = 1;
           }
           else if(c.gX >= 0.9 && c.gX <= 1.8 && c.passou == 1)
           {
               c.gX += 0.0048;
               c.gR -= 0.2;
           }
           
           if(c.gR > -270 && c.gX >= 1.8 && c.passou == 1)
           {
               c.reta3 = false;
               c.reta4 = true;
               c.passou = 0;
           }
        }
        
        if(c.reta4 == true)
        {
            if(c.gR > -270 && c.gX >= 1.8 && c.passou == 0)
            {
               gl.glTranslated(-0.25, 0, 0.4);
               gl.glRotated(90-c.gR, 0, 1, 0);
            }
            
            if(c.gZ > -7 && c.passou == 0)
                c.gZ -= 0.004 * randomValue;
            else if(c.gZ <= -7 && c.gZ >= -7.1 && c.passou == 0)
            {
                c.gR -= 60;
                c.gX -= 1.8;
                c.gZ += 7;
                c.passou = 1;
            }
            else if(c.gZ > -0.5 && c.passou == 1)
            {
                c.gZ -= 0.005;
                c.gR -= 0.2;
            }
            
            if(c.gZ <= -0.5 && c.passou == 1)
            {
                c.gR = c.gX = c.gY = c.gZ = 0;
                c.passou = 0;
                //gl.glTranslated(5, 0, 0);
                //gl.glRotated(gR, 0, 1, 0);
                c.reta4 = false;
                c.reta1 = true;
            }
        }
    }
}
