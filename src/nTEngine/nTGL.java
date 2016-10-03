package nTEngine;

import nTEngine.Util.nTColor;
import nTEngine.Util.nTScenes;
import nTEngine.Util.nTLoader;
import nTEngine.Util.nTPoint;
import nTEngine.Util.nTRectangle;
import nTEngine.gameObj.nTCamera;
import nTEngine.gameObj.nTFonts;
import nTEngine.gameObj.nTGameObj;
import nTEngine.gameObj.nTImageMatrix;
import nTEngine.gameObj.nTMesh;
import nTEngine.gameObj.nTRender;
import nTEngine.gameObj.nTTexture;
import nTEngine.nTMouse.button;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class nTGL {
    private int width,height;
    private boolean vSync;
    private final boolean resizeable,customReshape;
    public static final boolean fontAntiAlias=false;
    private final String windowName; 
    private long window;
    private int Scene;
    
    public nTGL(nTPoint windowSize,String windowName,double FPS,int firstScene,boolean canResize,boolean vSync,boolean customReshape){
        width=(int)windowSize.x;
        height=(int)windowSize.y;
        resizeable=canResize;
        this.windowName=windowName;
        this.vSync=vSync;
        this.customReshape=customReshape;
        this.Scene=firstScene;
        nTTimer.setFPS(FPS);
    }    
    
    //setup
    public void run() throws Exception{
            String osName = System.getProperty("os.name");
            System.out.println("LWJGL " + Version.getVersion() + "!");
            System.out.println("Running on "+osName);

            try {
                    init();
                    loop();

                    glfwFreeCallbacks(window);
                    glfwDestroyWindow(window);
            } finally {
                    glfwTerminate();
                    glfwSetErrorCallback(null).free();
            }
    }

    private void init() {
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit())throw new IllegalStateException("Unable to initialize GLFW");
            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            nTFonts.loadFonts();

            if(resizeable)
                glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
            else
                glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            window = glfwCreateWindow(width, height,windowName, NULL, NULL);
            if (window==NULL) throw new RuntimeException("Failed to create the GLFW window");

            //CallBacks
            glfwSetKeyCallback(window, this::keyboard);
            glfwSetMouseButtonCallback(window, this::mousePress);
            glfwSetCursorPosCallback(window, this::mouse);
            glfwSetScrollCallback(window, this::mouseScroll);
            if(customReshape)glfwSetWindowSizeCallback(window, this::reshape);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window,(vidmode.width()-width)/2,(vidmode.height()-height)/2);
            glfwMakeContextCurrent(window);

            if(vSync)
                glfwSwapInterval(1);
            else
                glfwSwapInterval(0);
            glfwShowWindow(window);
            
    }

    private void loop() throws Exception{
        GL.createCapabilities();
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, 0, height, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        
//   
//         GL11.glShadeModel(GL11.GL_SMOOTH);
//         GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
//         GL11.glClearDepth(1.0); 
//         GL11.glEnable(GL11.GL_DEPTH_TEST);
//         GL11.glDepthFunc(GL11.GL_LEQUAL); 
//         GL11.glMatrixMode(GL11.GL_PROJECTION); 
//         GL11.glLoadIdentity();
//         GL11.glFrustum(-(float)width/(float)height, (float)width/(float)height, -1.0f, 1.0f, 1.0f, 1000.0f);
//         GL11.glMatrixMode(GL11.GL_MODELVIEW);

        nTTimer.update();
        glClearColor(1.0f, 0.0f, 1.0f, 0.0f);
        
        //RENDER
        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
            draw();
            glfwSwapBuffers(window);
            nTMouse.unReleaseMouse();
            glfwPollEvents();
            nTTimer.sleepFPS();
        }
    }
    //callBacks    
    private void keyboard(long window, int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW_PRESS:
                
            break;
            case GLFW_RELEASE:
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(window, true);
                }
            break;
            case GLFW_REPEAT:
                
            break;
        }
    }
    
    private void mousePress(long window, int button, int action, int mods) {
        switch (action) {
            case GLFW_PRESS:
                switch(button){
                    case GLFW_MOUSE_BUTTON_1:   nTMouse.setLeft(new button(true)); break;
                    case GLFW_MOUSE_BUTTON_2:   nTMouse.setRight(new button(true));break;
                    case GLFW_MOUSE_BUTTON_3:   nTMouse.setMiddle(new button(true));break;
                }              
            break;
            case GLFW_RELEASE:
                switch(button){
                    case GLFW_MOUSE_BUTTON_1:   nTMouse.setLeft(new button(false)); break;
                    case GLFW_MOUSE_BUTTON_2:   nTMouse.setRight(new button(false));break;
                    case GLFW_MOUSE_BUTTON_3:   nTMouse.setMiddle(new button(false));break;
                }  
            break;
            case GLFW_REPEAT:
                
            break;
        }
    }
    
    private void mouse(long window, double xpos, double ypos) {
        nTMouse.setPos(new nTPoint(xpos,ypos));
    }
    
    private void mouseScroll(long window, double xoffset, double yoffset) {
        nTMouse.setScrollUp(yoffset);
    }
    
    private void reshape(long window, int width, int height) {
        this.height=height;
        this.width=width;
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
        
//        GL11.glOrtho(0, width, 0, height, 1, -1);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        glViewport(0, 0, width, height);
    }

    //funcoes
    public boolean getvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
        if(vSync)
            glfwSwapInterval(1); 
        else
            glfwSwapInterval(0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void setglClearColor(double r,double g,double b,double a){
        glClearColor((float)r,(float)g,(float)b,(float)a);
    }
    
    public void setglClearColor(nTColor color){
        setglClearColor(color.R,color.G,color.B,color.A);
    }
    
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }
    
    //draw
    private void draw() throws Exception{
        if(Scene==nTScenes.test.id){
            if(!nTScenes.test.called){
                System.out.println("Cor do pixel (1,1)"+new nTImageMatrix(nTLoader.loadTextureMatrix("res/textures/paraboloideHiperbolico.png")).getPixel(1,1));
            }
           
//            
//            
//            GL11.glLoadIdentity(); 
//             GL11.glTranslatef(0f,0.0f,-7f);             
//         GL11.glRotatef(45f,0.0f,1.0f,0.0f);    
        
         nTMesh bunny = nTLoader.loadObj("res/objs/bunny.obj");
         nTGameObj bunnyO=new nTGameObj(bunny);
          bunnyO.setScale(10);
          
          nTCamera cam=new nTCamera();
            nTRender renderer=new nTRender();
            renderer.init();
            GL11.glColor3f(0.5f,0.5f,1.0f);  
            renderer.draw(cam, new nTGameObj[]{bunnyO});
            cam.movePosition(100, 100, 100);
            cam.moveRotation(0, 0,10);
            
            nTDraw.texture(new nTTexture(nTLoader.loadTexture("res/textures/paraboloideHiperbolico.png")), 
                    new nTRectangle(0,0,0,30,30,0), new nTColor(0x90,0x70,0xCD), 1);
            
         GL11.glColor3f(0.5f,0.5f,1.0f);  
         int scale=30;
         GL11.glBegin(GL11.GL_QUADS);    
            GL11.glColor3f(1.0f*scale,1.0f*scale,0.0f*scale);           
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale,-1.0f*scale);        
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale,-1.0f*scale);        
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale, 1.0f*scale);
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale, 1.0f*scale);  
            GL11.glColor3f(1.0f*scale,0.5f*scale,0.0f*scale);            
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale,-1.0f*scale);
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale,-1.0f*scale);
            GL11.glColor3f(1.0f*scale,0.0f*scale,0.0f*scale);
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale, 1.0f*scale);
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale, 1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glColor3f(1.0f*scale,1.0f*scale,0.0f*scale);
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale,-1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale,-1.0f*scale);
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale,-1.0f*scale);
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale,-1.0f*scale);
            GL11.glColor3f(0.0f*scale,0.0f*scale,1.0f*scale);
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale, 1.0f*scale);
            GL11.glVertex3f(-1.0f*scale, 1.0f*scale,-1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale,-1.0f*scale);
            GL11.glVertex3f(-1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glColor3f(1.0f*scale,0.0f*scale,1.0f*scale);
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale,-1.0f*scale);
            GL11.glVertex3f( 1.0f*scale, 1.0f*scale, 1.0f*scale);
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale, 1.0f*scale);
            GL11.glVertex3f( 1.0f*scale,-1.0f*scale,-1.0f*scale);
        GL11.glEnd();    
        }
        
        
        nTScenes.setAllNotCalledExcept(Scene);
    }
}
