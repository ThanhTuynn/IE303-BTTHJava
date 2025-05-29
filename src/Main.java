import java.awt.*;
import java.util.List;
import java.util.Arrays;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Shoe Shop");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(950, 600);
            window.setLayout(new BorderLayout());

            // Danh sách sản phẩm
            List<Product> shoeList = Arrays.asList(
                new Product("4DFWD PULSE SHOES", "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", "assets/img1.png"),
                new Product("FORUM MID SHOES", "Adidas", 100.00, "This product is excluded from all promotional discounts and offers.", "assets/img2.png"),
                new Product("SUPERNOVA SHOES", "Adidas", 150.00, "NMD City Stock 2", "assets/img3.png"),
                new Product("NMD R1 SHOES", "Adidas", 160.00, "NMD City Stock 2", "assets/img4.png"),
                new Product("ZX 2K BOOST SHOES", "Adidas", 120.00, "NMD City Stock 2", "assets/img5.png"),
                new Product("ULTRABOOST 22 SHOES", "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", "assets/img6.png"),
                new Product("ADIZERO ADIOS PRO 2 SHOES", "Adidas", 160.00, "This product is excluded from all promotional discounts and offers.", "assets/img1.png"),
                new Product("NITEBALL SHOES", "Adidas", 100.00, "This product is excluded from all promotional discounts and offers.", "assets/img2.png")
            );

            // Panel hiển thị chi tiết sản phẩm
            ProductDetailPanel detailView = new ProductDetailPanel();
            detailView.showProduct(shoeList.get(0));

            // Panel danh sách sản phẩm
            JPanel gridPanel = new JPanel(new GridLayout(0, 4, 25, 25));
            gridPanel.setBackground(new Color(240, 240, 240));
            gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Quản lý trạng thái chọn
            ProductCard[] currentSelected = new ProductCard[1];

            for (int idx = 0; idx < shoeList.size(); idx++) {
                Product prod = shoeList.get(idx);
                final ProductCard[] cardRef = new ProductCard[1];

                cardRef[0] = new ProductCard(prod, () -> {
                    detailView.showProduct(prod);
                    if (currentSelected[0] != null) {
                        currentSelected[0].setSelected(false);
                    }
                    cardRef[0].setSelected(true);
                    currentSelected[0] = cardRef[0];
                });

                gridPanel.add(cardRef[0]);
                if (idx == 0) {
                    cardRef[0].setSelected(true);
                    currentSelected[0] = cardRef[0];
                }
            }

            JScrollPane gridScroll = new JScrollPane(gridPanel);
            gridScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            window.add(detailView, BorderLayout.WEST);
            window.add(gridScroll, BorderLayout.CENTER);
            window.setVisible(true);
        });
    }
}