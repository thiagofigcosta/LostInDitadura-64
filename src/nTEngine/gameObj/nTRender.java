package nTEngine.gameObj;

import java.awt.Window;
import nTEngine.Util.nTLoader;
import org.joml.Matrix4f;

public class nTRender {

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 100f;

    private static final float Z_FAR = -100.f;

    private final nTTransformation transformation;

    private nTShader shader;

    private float specularPower;

    public nTRender() {
        transformation = new nTTransformation();
        specularPower = 10f;
    }

    public void init() throws Exception {
        // Create shader
        shader = new nTShader();
        shader.createVertexShader(nTLoader.loadText("res/shaders/vertex.vs"));
        shader.createFragmentShader(nTLoader.loadText("res/shaders/fragment.fs"));
        shader.link();
        shader.createUniform("projectionMatrix");
        shader.createUniform("modelViewMatrix");
        shader.createUniform("texture_sampler");
//        shader.createUniform("specularPower");
//        shader.createUniform("ambientLight");
    }

    public void draw(nTCamera camera, nTGameObj[] gameItems) {
        shader.bind();
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, 800, 600, Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shader.setUniform("texture_sampler", 0);
        for (nTGameObj gameItem : gameItems) {
            nTMesh mesh = gameItem.getMesh();
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);
            mesh.draw();
        }

        shader.unbind();
    }

    public void delete() {
        if (shader != null) {
            shader.delete();
        }
    }
}
