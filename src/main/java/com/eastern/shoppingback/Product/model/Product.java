package com.eastern.shoppingback.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.SpringVersion;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Table(name = "Product")
@NoArgsConstructor
@Data
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer category;
    private String imagePath;
    private Integer quantity;
    private Double price;
}
