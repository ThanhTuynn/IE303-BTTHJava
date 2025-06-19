package com.example.BTTH4;

import com.example.BTTH4.entity.Product;
import com.example.BTTH4.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        File file = new ClassPathResource("product-info.txt").getFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        String name = null, brand = null, description = null, image = null;
        String price = null;
        int imageIndex = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.toLowerCase().startsWith("image")) {
                imageIndex++;
                image = "img" + imageIndex + ".jpg";
            } else if (line.startsWith("- Name:")) {
                name = line.replace("- Name:", "").trim();
            } else if (line.startsWith("- Price:")) {
                price = line.replace("- Price:", "").trim();
            } else if (line.startsWith("- Brand:")) {
                brand = line.replace("- Brand:", "").trim();
            } else if (line.startsWith("- Description:")) {
                description = line.replace("- Description:", "").trim();
                Product p = new Product(name, price, brand, description, image);
                productRepository.save(p);
            }
        }
        reader.close();
    }
}
