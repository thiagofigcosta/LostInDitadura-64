package nTEngine.Util;

public class nTRectangle {
    public nTPoint p0,p1;
    
    public nTRectangle(nTPoint p0,nTPoint p1){
        this.p0=p0;
        this.p1=p1;
    }
    
    public nTRectangle(double x0,double y0,double z0,double x1,double y1,double z1){
        this.p0=new nTPoint(x0,y0,z0);
        this.p1=new nTPoint(x1,y1,z1);
    }
    
    public nTRectangle(double x0,double y0,double x1,double y1){
        this.p0=new nTPoint(x0,y0,0);
        this.p1=new nTPoint(x1,y1,0);
    }
}
