package nTEngine.gameObj;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import nTEngine.Util.nTColor;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class nTMesh {
    private static final nTColor defaultColor = new nTColor(1.0, 1.0, 1.0);
    private final List<Integer> vboIds;
    private final int vertexCount;
    
    private final int id;
    private nTTexture tex;
    private nTColor color;
    
    public nTMesh(float[]positions,float[]textCoords,float[]normals,int[]indices) {
        color=defaultColor;
        vertexCount = indices.length;
        vboIds = new ArrayList();
        id = glGenVertexArrays();
        glBindVertexArray(id);

        // Position VBO
        int vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer posBuffer = BufferUtils.createFloatBuffer(positions.length);
        posBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Texture coordinates VBO
        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer textCoordsBuffer = BufferUtils.createFloatBuffer(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        // Vertex normals VBO
        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer vecNormalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        vecNormalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

        // Index VBO
        vboId = glGenBuffers();
        vboIds.add(vboId);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public boolean haveTexture() {
        return this.tex != null;
    }
    
    public nTTexture getTexture() {
        return this.tex;
    }
    
    public void setTexture(nTTexture texture) {
        this.tex = texture;
    }
    
    public void setColour(nTColor color) {
        this.color = color;
    }

    public nTColor getColour() {
        return this.color;
    }

    public int getId() {
        return id;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void draw() {
        if (tex!=null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, tex.getId());
        }
        glBindVertexArray(getId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
//        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void delete() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIds) {
            glDeleteBuffers(vboId);
        }
        if (tex != null) {
            tex.delete();
        }
        glBindVertexArray(0);
        glDeleteVertexArrays(id);
    }    
}
