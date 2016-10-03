package nTEngine.gameObj;

import java.nio.ByteBuffer;
import nTEngine.Util.nTColor;

public class nTImageMatrix {
    public ByteBuffer matrix;
    public int width,height;
    
    public nTImageMatrix(ByteBuffer buffer,int Width,int Height){
        this.matrix=buffer;
        this.width=Width;
        this.height=Height;
    }
    
    public nTImageMatrix(nTImageMatrix img){
        this.matrix=img.matrix;
        this.width=img.width;
        this.height=img.height;
    }
    
    public nTColor getPixel(nTImageMatrix image,int x, int y) {
        byte[] pixels = new byte[width*height*4];
        matrix.get(pixels);
        if (y<=image.height && x<=image.width){
            int index=(x+y*image.width)*4;
            int r=pixels[index] & 0xFF;
            int g=pixels[index+1] & 0xFF;
            int b=pixels[index+2] & 0xFF;
            int a=pixels[index+3] & 0xFF;
            return new nTColor(r,g,b,a);
        }else{
          return new nTColor(0,0,0,1);
        }
    }
    
    public nTColor getPixel(int x, int y) {
        return getPixel(this,x,y);
    }
}
