package utility;

public class IdGenerator {
    private static Integer currentId = 0;

    public static Integer generateId() {
        currentId++;
        return currentId;
    }

    public static int getId(){
        return currentId;
    }
    public static void setId(Integer id){
        currentId = id;
    }
}
