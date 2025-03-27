//Bài 2: Xấp xỉ giá trị của pi thông qua đường tròn đơn vị (tâm O(0, 0) bán kính r=1$).
import java.util.Random;

public class bai2GiaTriSoPi{
    public static void main(String[] args) {
        int numPoints = 1000000; // Số điểm ngẫu nhiên
        double piApprox = approximatePi(numPoints);
        System.out.println("Giá trị xấp xỉ của pi: " + piApprox);
        System.out.println("Giá trị thực của pi:    " + Math.PI);
        System.out.println("Sai so giữa giá trị xấp xỉ và giá trị thực của pi: " + Math.abs(piApprox - Math.PI));
    }

    public static double approximatePi(int numPoints) {
        Random random = new Random();
        int insideCircle = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = (random.nextDouble() * 2) - 1; // Giá trị x từ -1 đến 1
            double y = (random.nextDouble() * 2) - 1; // Giá trị y từ -1 đến 1

            if (x * x + y * y <= 1) {
                insideCircle++;
            }
        }

        return (double) insideCircle / numPoints * 4;
    }
}
