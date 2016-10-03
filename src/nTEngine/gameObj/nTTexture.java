package nTEngine.gameObj;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class nTTexture {
    private final int id,width,height;

    public nTTexture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }
    
    public nTTexture(nTTexture tex) {
        this.id = tex.id;
        this.width = tex.width;
        this.height = tex.height;
    }


    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void delete() {
        glDeleteTextures(id);
    }
    
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
