package nTEngine.Util;

import org.lwjgl.opengl.GL11;

public class nTColor {
    public double R,G,B,A;
    private static nTColor current=new nTColor(1,1,1);

    @Override
    public String toString() {
        return "nTColor{" + "R=" + R + ", G=" + G + ", B=" + B + ", A=" + A + '}';
    }
    public nTColor(double R,double G,double B){
        if(R>1)
            R/=255;
        if(G>1)
            G/=255;
        if(B>1)
            B/=255;
        if(R<0)
            R=0;
        if(G<0)
            G=0;
        if(B<0)
            B=0;
        this.R=R;
        this.G=G;
        this.B=B;
        this.A=1;
    }
    
    public nTColor(){
        this.R=0;
        this.G=0;
        this.B=0;
        this.A=1;
    }
    
    public nTColor(double R,double G,double B,double A){
        if(R>1)
            R/=255;
        if(G>1)
            G/=255;
        if(B>1)
            B/=255;
        if(A>1)
            A=1;
        if(R<0)
            R=0;
        if(G<0)
            G=0;
        if(B<0)
            B=0;
        if(A<0)
            A=0;
        this.R=R;
        this.G=G;
        this.B=B;
        this.A=A;
    }
    
    public nTColor(nTColor color){
        this.R=color.R;
        this.G=color.G;
        this.B=color.B;
        this.A=color.A;
    }
    
    public static void setColor(nTColor color){
        current=color;
        GL11.glColor4d(color.R,color.G,color.B,color.A);
    }

    public static nTColor getCurrent() {
        return current;
    }
}
