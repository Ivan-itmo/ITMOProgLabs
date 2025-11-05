import java.util.Random;
import java.util.Arrays;
public class Main{
    public static boolean check(int []mas, long value){
        for (int i = 0; i < 10; i++){
            if (mas[i] == value){
                return true;
            }
        }
        return false;
    }
    public static void FillArray(double [][]mas, long []masw, double []masx, int []num, int n, int m){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (masw[i] == 18){
                    mas[i][j] = Math.sin(0.5/(Math.sin(masx[j])+1));
                }
                else if (check(num, masw[i]) == true){
                    mas[i][j] = Math.sin(Math.pow(0.5*masx[j], 2));
                }
                else{
                    mas[i][j] = Math.asin(Math.sin(Math.asin(0.1 * Math.pow(Math.E, -Math.abs(masx[j])))));
                }
            }
        }
    }
    public static void PrintArray(double [][]mas, int n, int m){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                System.out.printf("%6.3f\t", mas[i][j]);
            }
            System.out.println();
        }
    }
    public static void main (String[] args){
        Random rnd = new Random();
        long w[] = new long[20];
        double x[] = new double[11];
        Arrays.fill(x, -100);
        for (long i = 0; i < 20; i++){
            w[(int) i] = i+1;
        }
        int c = 0;
        while (x[10] == -100){
            double q = rnd.nextDouble(23) - 7;
            if (q <= 15){
                x[c] = q;
                c += 1;
            }
        }
        int[] num = {2, 4, 6, 10, 12, 13, 14, 17, 19, 20};
        double w1[][] = new double [20][11];
        FillArray(w1, w, x, num, 20, 11);
        PrintArray(w1, 20, 11);

    }
}