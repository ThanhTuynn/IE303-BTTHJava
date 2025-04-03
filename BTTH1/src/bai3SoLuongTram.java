//Bài 3: Tại một khu bảo tồn, người ta tìm cách xây dựng nên các trạm phát sóng vô tuyến để theo dõi vị trí của các con vật được thả trong một khu bảo tồn được mô tả trong hình trên.
//Người ta đã lắp đặt các trạm phát sóng vô tuyến để tiến hành thu tín hiệu vị trí của động vật được thả trong khu bảo tồn. Trong số các trạm phát này, sẽ có một số trạm nhất định đóng vai trò làm trạm gửi tín hiệu khẩn cấp về trung tâm (trạm cảnh báo) khi có một con thú rời khỏi phạm vi của khu bảo tồn. Con vật được xem là rời khỏi khu vực bảo tồn khi nó di chuyển ra khỏi tầm phủ sóng của tất cả các trạm vô tuyến.
//Hãy xây dựng thuật toán xác định các trạm cảnh báo từ các trạm phát sóng vô tuyến đã được xây dựng sẵn.
//Input: Số lượng trạm cảnh báo và toạ độ tương ứng cho từng trạm.
//Output: Toạ độ của các trạm được sử dụng làm trạm cảnh báo.
import java.util.*;

class Point {
    double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class bai3SoLuongTram{

    public static List<Point> findWarningStations(List<Point> stations) {
        if (stations.size() < 3) return stations; // Nếu số trạm < 3 thì tất cả đều là trạm cảnh báo

        // Sắp xếp theo hoành độ (nếu bằng thì theo tung độ)
        stations.sort(Comparator.comparingDouble((Point p) -> p.x).thenComparingDouble(p -> p.y));

        List<Point> hull = new ArrayList<>();

        // Tìm bao lồi theo thuật toán Graham scan
        for (int i = 0; i < 2; i++) {
            int start = hull.size();
            for (Point p : stations) {
                while (hull.size() >= start + 2 && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
                    hull.remove(hull.size() - 1);
                }
                hull.add(p);
            }
            hull.remove(hull.size() - 1);
            Collections.reverse(stations);
        }

        return hull;
    }

    private static double crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số lượng trạm phát sóng: ");
        int n = scanner.nextInt();

        List<Point> stations = new ArrayList<>();
        System.out.println("Nhập tọa độ của từng trạm (x y): ");
        for (int i = 0; i < n; i++) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            stations.add(new Point(x, y));
        }

        List<Point> warningStations = findWarningStations(stations);

        System.out.println("Các trạm cảnh báo:");
        for (Point p : warningStations) {
            System.out.println(p.x + " " + p.y);
        }

        scanner.close();
    }
}

