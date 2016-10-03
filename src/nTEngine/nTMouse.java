package nTEngine;

import nTEngine.Util.nTPoint;

public class nTMouse {
    public static class button{
        public boolean pressed,released;
        public button(boolean pressed){
            this.pressed=pressed;
            this.released=!pressed;
        }
        public button(){
            this.pressed=false;
            this.released=false;
        }
    }

    public static button getLeft() {
        return left;
    }

    public static void setLeft(button left) {
        nTMouse.left = left;
    }

    public static button getRight() {
        return right;
    }

    public static void setRight(button right) {
        nTMouse.right = right;
    }

    public static button getMiddle() {
        return middle;
    }

    public static void setMiddle(button middle) {
        nTMouse.middle = middle;
    }

    public static nTPoint getPos() {
        return pos;
    }

    public static void setPos(nTPoint pos) {
        nTMouse.pos = pos;
    }

    public static double getScrollUp() {
        return scrollUp;
    }

    public static void setScrollUp(double scrollUp) {
        nTMouse.scrollUp = scrollUp;
    }
    
    public static void unReleaseMouse(){
        if(left.released)
            left.released=false;
        if(right.released)
            right.released=false;
        if(middle.released)
            middle.released=false;
    }
    
    private static button left=new button(),right=new button(),middle=new button();
    private static nTPoint pos=new nTPoint();
    private static double scrollUp;
}
