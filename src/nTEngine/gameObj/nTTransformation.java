package nTEngine.gameObj;

import nTEngine.Util.nTPoint;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class nTTransformation {
    private final Matrix4f projectionMatrix;
    private final Matrix4f modelViewMatrix;
    private final Matrix4f viewMatrix;

    public nTTransformation() {
        projectionMatrix=new Matrix4f();
        modelViewMatrix=new Matrix4f();
        viewMatrix=new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(double fov,double width,double height,double zNear,double zFar) {
        double aspectRatio=width/height;        
        projectionMatrix.identity();
        projectionMatrix.perspective((float)fov, (float)aspectRatio, (float)zNear, (float)zFar);
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix(nTCamera camera) {
        nTPoint cameraPos=camera.getPosition();
        nTPoint rotation=camera.getRotation();
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(rotation.x),new Vector3f(1,0,0)).rotate((float)Math.toRadians(rotation.y),new Vector3f(0,1,0));
        viewMatrix.translate((float)-cameraPos.x,(float)-cameraPos.y,(float)-cameraPos.z);
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix(nTGameObj gameObj, Matrix4f viewMatrix) {
        nTPoint rotation = gameObj.getRotation();
        Vector3f tmp=new Vector3f((float)gameObj.getPosition().x,(float)gameObj.getPosition().y,(float)gameObj.getPosition().z);
        modelViewMatrix.identity().translate(tmp).rotateX((float)Math.toRadians(-rotation.x)).rotateY((float)Math.toRadians(-rotation.y)).rotateZ((float)Math.toRadians(-rotation.z)).scale(gameObj.getScale());
        Matrix4f viewCurr=new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }
}
