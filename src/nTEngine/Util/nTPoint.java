package nTEngine.Util;

public class nTPoint {
    public double x,y,z;
    
    public nTPoint(){
        this.x=0;
        this.y=0;
        this.z=0;
    }
    
    public nTPoint(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    
    public nTPoint(double x, double y){
        this.x=x;
        this.y=y;
    }
}
