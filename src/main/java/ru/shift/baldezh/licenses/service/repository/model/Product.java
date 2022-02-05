package ru.shift.baldezh.licenses.service.repository.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private long productId;

    @NonNull
    @Column(nullable = false)
    private String productName;

    @NonNull
    @Column(nullable = false)
    private String productVersion;

    public Product() {
    }

    public Product(@NonNull String productName, @NonNull String productVersion) {
        this.productName = productName;
        this.productVersion = productVersion;
    }

    public Product(Long id) {
        this.productId = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(@NonNull String productVersion) {
        this.productVersion = productVersion;
    }
}
