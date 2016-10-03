package nTEngine.gameObj;

import nTEngine.Util.nTPoint;

public class nTGameObj {
    private final nTMesh mesh;
    private final nTPoint position;
    private float scale;
    private final nTPoint rotation;

    public nTGameObj(nTMesh mesh) {
        this.mesh=mesh;
        position=new nTPoint(0, 0, 0);
        scale=1;
        rotation=new nTPoint(0, 0, 0);
    }

    public nTPoint getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public nTPoint getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public nTMesh getMesh() {
        return mesh;
    }
}
