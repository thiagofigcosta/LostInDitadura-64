package nTEngine;

import nTEngine.gameObj.nTTexture;
import nTEngine.Util.nTColor;
import nTEngine.Util.nTPoint;
import nTEngine.Util.nTRectangle;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class nTDraw {
    public static void texture(nTTexture tex,nTRectangle pos,nTColor color,int ori){
        nTColor tmp=nTColor.getCurrent();
        nTColor.setColor(color);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        tex.bind(); 
        GL11.glBegin(GL11.GL_QUADS);
        if(ori<=0){
          GL11.glTexCoord2f(1, 1); GL11.glVertex3d(pos.p0.x, pos.p0.y, pos.p0.z);
          GL11.glTexCoord2f(0, 1); GL11.glVertex3d(pos.p1.x, pos.p0.y, pos.p0.z);
          GL11.glTexCoord2f(0, 0); GL11.glVertex3d(pos.p1.x, pos.p1.y, pos.p0.z);
          GL11.glTexCoord2f(1, 0); GL11.glVertex3d(pos.p0.x, pos.p1.y, pos.p0.z);
        }else if(ori==1){
          GL11.glTexCoord2f(0, 1); GL11.glVertex3d(pos.p0.x, pos.p0.y, pos.p0.z);
          GL11.glTexCoord2f(1, 1); GL11.glVertex3d(pos.p1.x, pos.p0.y, pos.p0.z);
          GL11.glTexCoord2f(1, 0); GL11.glVertex3d(pos.p1.x, pos.p1.y, pos.p0.z);
          GL11.glTexCoord2f(0, 0); GL11.glVertex3d(pos.p0.x, pos.p1.y, pos.p0.z);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        nTColor.setColor(tmp);
    }
    
    public static void rectangle(nTRectangle pos,nTColor color){
        nTColor tmp=nTColor.getCurrent();
        nTColor.setColor(color); 
        GL11.glBegin(GL11.GL_QUADS);
          GL11.glVertex3d(pos.p0.x, pos.p0.y, pos.p0.z);
          GL11.glVertex3d(pos.p1.x, pos.p0.y, pos.p0.z);
          GL11.glVertex3d(pos.p1.x, pos.p1.y, pos.p0.z);
          GL11.glVertex3d(pos.p0.x, pos.p1.y, pos.p0.z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        nTColor.setColor(tmp);
    }
    
    public static void text(String text,nTPoint pos,TrueTypeFont font, nTColor color){
        Color tmp=new Color((float)color.R,(float)color.G,(float)color.B,(float)color.A);
        font.drawString((float)pos.x, (float)pos.y,text, tmp);
    }
}
