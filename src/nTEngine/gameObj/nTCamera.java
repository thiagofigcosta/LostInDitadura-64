package nTEngine.gameObj;

import nTEngine.Util.nTPoint;

public class nTCamera {
    private final nTPoint position;
    private final nTPoint rotation;
    
    public nTCamera() {
        position = new nTPoint(0, 0, 0);
        rotation = new nTPoint(0, 0, 0);
    }
    
    public nTCamera(nTPoint position, nTPoint rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public nTPoint getPosition() {
        return position;
    }

    public void setPosition(double x, double y, double z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    public void movePosition(double offsetX, double offsetY, double offsetZ) {
        if (offsetZ!=0){
            position.x+=(double)Math.sin(Math.toRadians(rotation.y))*-1.0f*offsetZ;
            position.z+=(double)Math.cos(Math.toRadians(rotation.y))*offsetZ;
        }
        if (offsetX!=0) {
            position.x+=(double)Math.sin(Math.toRadians(rotation.y - 90))*-1.0f*offsetX;
            position.z+=(double)Math.cos(Math.toRadians(rotation.y - 90))*offsetX;
        }
        position.y+=offsetY;
    }

    public nTPoint getRotation() {
        return rotation;
    }
    
    public void setRotation(double x,double y,double z) {
        rotation.x=x;
        rotation.y=y;
        rotation.z=z;
    }

    public void moveRotation(double offsetX, double offsetY, double offsetZ) {
        rotation.x+=offsetX;
        rotation.y+=offsetY;
        rotation.z+=offsetZ;
    }
}
