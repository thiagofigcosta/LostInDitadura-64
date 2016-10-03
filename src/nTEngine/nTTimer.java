package nTEngine;

public class nTTimer {
    private static double current;
    private static double FPS=30;
    
    public static void update(){
        current=System.nanoTime()/1000;
    }
    
    public static double getMs(){
        return 1000f/FPS;
    }
    
    public static double getUs(){
        return 1000000f/FPS;
    }
    
    
    public static void setFPS(double FPS_){
        FPS=FPS_;
    }
    
    public static double getFPS(){
        return FPS;
    }
    
    public static double getTime(){
        update();
        return current;
    }
    
    private static void sleepMs(double time){
        try {
            Thread.sleep((long) time);
        } catch (InterruptedException ie) {
        }
    }
    
    public static void sleepFPS(){
        double atual=getTime();
        while(atual+getMs()<getTime())
            sleepMs(1);
    }
    
    public boolean checkTimer(double time){
        double atual=getTime();
        return atual+time<getTime();
    }
    
}
