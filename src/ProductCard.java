import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
 
public class ProductCard extends JPanel {
    private boolean isSelected = false;

    public ProductCard(Product product, Runnable onClick) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setPreferredSize(new Dimension(210, 260));
        setMaximumSize(new Dimension(210, 260));
        setBorder(createBorder(false));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClick.run();
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!isSelected) setBackground(new Color(230, 240, 255));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!isSelected) setBackground(new Color(245, 245, 245));
            }
        });

        // Tên sản phẩm
        JLabel name = new JLabel("<html><b>" + truncate(product.getName(), 18) + "</b></html>");
        name.setFont(new Font("Arial", Font.BOLD, 16));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mô tả
        JLabel desc = new JLabel("<html><div style='width:170px;'>" + truncate(product.getDescription(), 32) + "</div></html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 11));
        desc.setForeground(Color.GRAY);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ảnh sản phẩm
        JLabel imgLabel = createResizedImageLabel(product.getImagePath(), 220, 160);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thương hiệu và giá
        JLabel brand = new JLabel(product.getBrand());
        brand.setFont(new Font("Arial", Font.PLAIN, 13));
        brand.setForeground(Color.DARK_GRAY);

        JLabel price = new JLabel(String.format("$%.2f", product.getPrice()));
        price.setFont(new Font("Arial", Font.BOLD, 22)); // Size lớn hơn
        price.setForeground(Color.BLACK); // Màu đen

        JPanel infoRow = new JPanel(new BorderLayout());
        infoRow.setOpaque(false);
        infoRow.add(brand, BorderLayout.WEST);
        infoRow.add(price, BorderLayout.EAST);

        add(Box.createVerticalStrut(8));
        add(name);
        add(desc);
        add(Box.createVerticalStrut(5));
        add(imgLabel);
        add(Box.createVerticalStrut(8));
        add(infoRow);
        add(Box.createVerticalGlue());
    }

    private Border createBorder(boolean selected) {
        Color borderColor = selected ? new Color(30, 144, 255) : new Color(220, 220, 220);
        return BorderFactory.createCompoundBorder(
            new RoundedLineBorder(borderColor, selected ? 2 : 1, 18, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );
    }

    private JLabel createResizedImageLabel(String imagePath, int maxWidth, int maxHeight) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            int imgWidth = icon.getIconWidth();
            int imgHeight = icon.getIconHeight();
    
            // Nếu ảnh lỗi hoặc không tồn tại
            if (imgWidth <= 0 || imgHeight <= 0) {
                JLabel errorLabel = new JLabel("No Image");
                errorLabel.setPreferredSize(new Dimension(maxWidth, maxHeight));
                errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                errorLabel.setForeground(Color.RED);
                return errorLabel;
            }
    
            // Tính tỉ lệ scale
            double widthRatio = (double) maxWidth / imgWidth;
            double heightRatio = (double) maxHeight / imgHeight;
            double scale = Math.min(widthRatio, heightRatio);
    
            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);
    
            Image scaled = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled));
            label.setPreferredSize(new Dimension(maxWidth, maxHeight));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("No Image");
            errorLabel.setPreferredSize(new Dimension(maxWidth, maxHeight));
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            return errorLabel;
        }
    }

    private String truncate(String text, int maxLength) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        setBorder(createBorder(selected));
        setBackground(selected ? new Color(230, 240, 255) : new Color(245, 245, 245));
    }

    // Custom bo góc
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
        g2.dispose();
        super.paintComponent(g);
    }
}

// --- RoundedLineBorder class ---
class RoundedLineBorder extends LineBorder {
    private int radius;
    public RoundedLineBorder(Color color, int thickness, int radius, boolean roundedCorners) {
        super(color, thickness, roundedCorners);
        this.radius = radius;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(lineColor);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}