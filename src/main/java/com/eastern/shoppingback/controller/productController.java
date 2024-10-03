package com.eastern.shoppingback.controller;
import org.springframework.core.io.Resource;
import com.eastern.shoppingback.model.Product;
import com.eastern.shoppingback.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class productController {

    private final String UPLOAD_DIR = "src/main/resources/static/images/products/";
    @Autowired
    ProductService productService;

    @GetMapping("/data")
    public String data(){
        return "working";
    }

//    @GetMapping("/all")
//    public List<Product> getAllProducts(){
//        return productService.getAll();
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/{id}")
    public Optional<Product> getProductById(@PathVariable int id) {
        return productService.getById(id);
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {

        Path imgPath = Paths.get(UPLOAD_DIR, imageName);

        try {
            UrlResource resource = new UrlResource(imgPath.toUri());
            String contentType = Files.probeContentType(imgPath);

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                // Image file does not exist or is not readable
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            // Log the error instead of printing to the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            // Log the error instead of printing to the console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






    @PostMapping("/add")
    public Product addProduct(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") int category,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile image) {

        // Handle the file upload (you can store it in a file system or database)
        String savedImagePath=null;
        if (!image.isEmpty()) {
            try {
                // Generate a unique file name to avoid overwriting
                String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);

                // Save the image to the file system
                Files.copy(image.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

                savedImagePath =uniqueFileName;
                System.out.println("Image saved to: " + savedImagePath);

            } catch (IOException e) {
                System.out.println("==================== ERROR :" + e.toString());;
            }
        }
        Product product = new Product(-1,
                name,
                description,
                category,
                savedImagePath,
                quantity,
                price);

        // Return a response
        return productService.savep(product);
    }


    @PostMapping("/saveBulk")
    public void saveProductBulk(@RequestBody List<Product> p){
        productService.savep(p);
    }
}
