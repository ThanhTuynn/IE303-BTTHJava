// Bài 1: Chỉ sử dụng bán kính r, không được sử dụng bất kỳ hằng số nào khác, hãy xấp xỉ diện tích của hình tròn tâm O(0 ,0) bán kính r.
import java.util.Random;
import java.util.Scanner;

public class bai1BanKinhHinhTron{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập bán kính r: ");
        double r = scanner.nextDouble();

        int numPoints = 1000000; // Số điểm ngẫu nhiên
        double area = approximate(r, numPoints);
        System.out.println("Dien tich xap xi cua hinh tron ban kinh " + r + " la: " + area);

    }

    public static double approximate(double r, int numPoints) {
        Random random = new Random();
        int points = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = (random.nextDouble() * 2 - 1) * r;
            double y = (random.nextDouble() * 2 - 1) * r;
            if (x * x + y * y <= r * r) {
                points++;
            }
        }

        double squareArea = (2 * r) * (2 * r);
        return (double) points/ numPoints * squareArea;
    }
}