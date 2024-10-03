package com.manisha.ecommerce.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//@Data    #LOMBOK IS NOT WORKING
//@AllArgsConstructor 
@Component
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //auto generate the prodId and increment it by 1
	private int prodId;
	private String prodName;
	private String description;
	private String brand;
	private BigDecimal price;
	private String category;
	//using jackson library to change the date format 
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
	private Date releaseDate;
	private boolean available;
	private int quantity;
	//fields for product image
	private String imageName;
	private String imageType;
	//we can have the images stored on cloud storage and have the link for it and we can store the URL of this cloud storage in the database.
	//For this project to keep it simple, we are storing the images in the database in byte format. byte arrays should be stored in DBs as large object (Lob). NOT RECOMMENDED FOR PRODUCTION.
	@Lob
	private byte[] imageData;
	
	public Product() {
		super();
	}
	
	public Product(int prodId, String prodName, String description, String brand, BigDecimal price, String category,
			Date releaseDate, boolean available, int quantity, String imageName, String imageType, byte[] imageData) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.description = description;
		this.brand = brand;
		this.price = price;
		this.category = category;
		this.releaseDate = releaseDate;
		this.available = available;
		this.quantity = quantity;
		this.imageName = imageName;
		this.imageType = imageType;
		this.imageData = imageData;
	}
	
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodName=" + prodName + ", description=" + description + ", brand="
				+ brand + ", price=" + price + ", category=" + category + ", releaseDate=" + releaseDate
				+ ", available=" + available + ", quantity=" + quantity + ", imageName=" + imageName + ", imageType="
				+ imageType + ", imageData=" + Arrays.toString(imageData) + "]";
	}
		
}
