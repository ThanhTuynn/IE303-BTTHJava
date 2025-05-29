import java.awt.*;
import javax.swing.*;

public class ProductDetailPanel extends JPanel {
    private JLabel imageLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel priceLabel = new JLabel();
    private JLabel brandLabel = new JLabel();
    private JLabel descriptionLabel = new JLabel();

    // Biến alpha cho hiệu ứng mờ
    private float alpha = 1.0f;

    public ProductDetailPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(350, 600));
        setBackground(new Color(240, 240, 240)); // Màu nền xám nhạt

        // Panel chứa ảnh sản phẩm
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.NORTH);

        // Panel chứa thông tin sản phẩm
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(240, 240, 240)); // Màu nền xám nhạt
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(separator);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(priceLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(brandLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descriptionLabel.setForeground(new Color(120, 120, 120));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(descriptionLabel);

        add(textPanel, BorderLayout.CENTER);

        // Không có viền
        setBorder(null);
    }

    public void showProduct(Product p) {
        // Hiệu ứng fade out
        Timer fadeOut = new Timer(15, null);
        fadeOut.addActionListener(e -> {
            alpha -= 0.1f;
            if (alpha <= 0f) {
                alpha = 0f;
                fadeOut.stop();
                // Cập nhật nội dung sản phẩm
                updateProduct(p);
                // Hiệu ứng fade in
                Timer fadeIn = new Timer(15, null);
                fadeIn.addActionListener(ev -> {
                    alpha += 0.1f;
                    if (alpha >= 1f) {
                        alpha = 1f;
                        fadeIn.stop();
                    }
                    repaint();
                });
                fadeIn.start();
            }
            repaint();
        });
        fadeOut.start();
    }

    private void updateProduct(Product p) {
        nameLabel.setText(p.getName());
        priceLabel.setText(String.format("$%.2f", p.getPrice()));
        brandLabel.setText(p.getBrand());
        descriptionLabel.setText("<html><div style='text-align:left;'>" + p.getDescription() + "</div></html>");
        try {
            ImageIcon icon = new ImageIcon(p.getImagePath());
            int imgWidth = icon.getIconWidth();
            int imgHeight = icon.getIconHeight();
            int maxWidth = 340;
            int maxHeight = 240;

            if (imgWidth > 0 && imgHeight > 0) {
                double widthRatio = (double) maxWidth / imgWidth;
                double heightRatio = (double) maxHeight / imgHeight;
                double scale = Math.min(widthRatio, heightRatio);

                int newWidth = (int) (imgWidth * scale);
                int newHeight = (int) (imgHeight * scale);

                Image scaled = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
                imageLabel.setText("");
                imageLabel.setPreferredSize(new Dimension(maxWidth, maxHeight));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("No Image");
            }
        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("No Image");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);
        g2.dispose();
    }
}