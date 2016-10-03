package nTEngine.gameObj;

import java.awt.Font;
import nTEngine.nTGL;
import org.newdawn.slick.TrueTypeFont;

public class nTFonts {
    public static TrueTypeFont timeNewRoman24;
    
    
    //TEMPORARIO
    static Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
    TrueTypeFont font = new TrueTypeFont(awtFont, false);
    //TEMPORARIO
    
    
    public static void loadFonts(){
       //timeNewRoman24=new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), nTGL.fontAntiAlias);
    }
}
