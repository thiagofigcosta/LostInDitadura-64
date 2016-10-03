package nTEngine.gameObj;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import nTEngine.Util.nTPoint;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class nTShader {
    private final int id;

    private int vertexShaderId;

    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public nTShader() throws Exception {
        id = glCreateProgram();
        if (id == 0) {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(id, uniformName);
        if (uniformLocation < 0) {
            throw new Exception ("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        value.get(fb);
        glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, nTPoint value) {
        glUniform3f(uniforms.get(uniformName), (float)value.x, (float)value.y, (float)value.z );
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Code: " + shaderId);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(id, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: "+glGetProgramInfoLog(id,1024));}

        glValidateProgram(id);
        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: "+glGetProgramInfoLog(id,1024));
        }

    }

    public void bind() {
        glUseProgram(id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void delete() {
        unbind();
        if (id != 0) {
            if (vertexShaderId != 0) {
                glDetachShader(id, vertexShaderId);
            }
            if (fragmentShaderId != 0) {
                glDetachShader(id,fragmentShaderId);
            }
            glDeleteProgram(id);
        }
    }
}
