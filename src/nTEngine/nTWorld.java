package nTEngine;

import java.awt.Window;
import nTEngine.Util.nTColor;
import nTEngine.Util.nTLoader;
import nTEngine.Util.nTPoint;
import nTEngine.gameObj.nTCamera;
import nTEngine.gameObj.nTGameObj;
import nTEngine.gameObj.nTMesh;
import nTEngine.gameObj.nTShader;
import nTEngine.gameObj.nTTransformation;
import org.joml.Matrix4f;

public class nTWorld {
    private static final float fov = (float) Math.toRadians(60.0f);
    private static final float zNear = 0.01f;
    private static final float zFar = 1000.f;
    private final nTTransformation transformation;
    private nTShader shader;

    public nTWorld() {
        transformation = new nTTransformation();
    }

    public void init(Window window) throws Exception {
        //cria o shader
        shader = new nTShader();
        shader.createVertexShader(nTLoader.loadText("/shaders/vertex.vs"));
        shader.createFragmentShader(nTLoader.loadText("/shaders/fragment.fs"));
        shader.link();
        shader.createUniform("projectionMatrix");
        shader.createUniform("modelViewMatrix");
        shader.createUniform("texture_sampler");
        shader.createUniform("colour");
        shader.createUniform("useColour");
    }

    public void draw(Window window, nTCamera camera, nTGameObj[] gameObjs) {
//        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        glViewport(0, 0, window.getWidth(), window.getHeight());
        shader.bind();
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(fov, window.getWidth(), window.getHeight(), zNear, zFar);
        shader.setUniform("projectionMatrix", projectionMatrix);
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shader.setUniform("texture_sampler", 0);
        for(nTGameObj gameObj : gameObjs) {
            nTMesh mesh = gameObj.getMesh();
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameObj, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);
            nTColor tmp=mesh.getColour();
            shader.setUniform("colour",new nTPoint(tmp.R,tmp.G,tmp.B));
            shader.setUniform("useColour", mesh.haveTexture() ? 0 : 1);
            mesh.draw();
        }

        shader.unbind();
    }

    public void delete() {
        if (shader!=null) {
            shader.delete();
        }
    }   
}
