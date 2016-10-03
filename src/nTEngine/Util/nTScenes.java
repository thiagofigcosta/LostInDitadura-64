package nTEngine.Util;

public class nTScenes {
    public static class Scene{
        public final int id;
        public boolean called;
        public Scene(int id){
            this.id=id;
            called=false;
        }
    }
    public static Scene test=new Scene(-1);
    
    public static void setAllNotCalledExcept(int id){
        test.called=false;
        
        if(id==test.id) test.called=true;
    }
}
