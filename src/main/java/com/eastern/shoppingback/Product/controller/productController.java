package com.eastern.shoppingback.Product.controller;
import com.eastern.shoppingback.Product.model.Product;
import com.eastern.shoppingback.Product.service.ProductService;
import org.springframework.core.io.Resource;
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

    @PutMapping("/update/simple")
    public Product updateProductObj(@RequestBody Product product){
        Optional<Product> p = productService.getById(product.getId());
        p.ifPresent(pr -> {
            product.setImagePath(pr.getImagePath());
        });
        return productService.savep(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id){
         productService.delete(id);
    }

    @PutMapping("/update")
    public Product updateProduct(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") int category,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") double price,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // Handle the file upload if the image is provided
        System.out.println("product updated");
        String savedImagePath = null;
        if (image != null && !image.isEmpty()) {
            try {
                Optional<Product> originalProduct = productService.getById(id);
                originalProduct.ifPresent(product -> {
                    deleteImage(product.getImagePath());
                });

                // Generate a unique file name to avoid overwriting
                String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + uniqueFileName);

                // Save the image to the file system
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                savedImagePath = uniqueFileName;
                System.out.println("Image saved to: " + savedImagePath);

            } catch (IOException e) {
                System.out.println("==================== ERROR :" + e.toString());
            }
        } else {
            // If no image is provided, retain the original image path
            Optional<Product> originalProduct = productService.getById(id);
            if (originalProduct.isPresent()) {
                savedImagePath = originalProduct.get().getImagePath();
            }
        }

        // Create a new Product object with the updated details
        Product product = new Product(
                id,
                name,
                description,
                category,
                savedImagePath,
                quantity,
                price
        );

        // Save the updated product and return it
        return productService.savep(product);
    }


    @DeleteMapping("/deleteImage/{imageName}")
    public void deleteImage(String imageName){
        String imagePathStr = UPLOAD_DIR + imageName;
        Path imagePath = Paths.get(imagePathStr);
        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            System.out.println("no image to delete");
        }
    }


    @PostMapping("/saveBulk")
    public void saveProductBulk(@RequestBody List<Product> p){
        productService.savep(p);
    }
}
