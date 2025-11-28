package models;

public enum Color {
    RED,
    BLACK,
    BLUE,
    ORANGE,
    WHITE,
    BROWN;

    public static String names(){
        StringBuilder sbList = new StringBuilder();
        for(Difficulty d : Difficulty.values()){
            sbList.append(d.name()).append(", ");
        }
        return sbList.substring(0, sbList.length()-2);
    }
}
