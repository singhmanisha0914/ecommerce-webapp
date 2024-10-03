package com.manisha.ecommerce.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.manisha.ecommerce.model.Product;
import com.manisha.ecommerce.repository.ProductRepo;

@Service
public class ProductService {
	
	private ProductRepo repo;
	
	//Using constructor injection to create repo bean
	public ProductService(ProductRepo repo) {
		super();
		this.repo = repo;
	}

	/*
	//Let's create dummy products for initial development
	List<Product> products = new ArrayList<>(Arrays.asList(new Product(101, "Cover", 555), 
			new Product(102, "Blanket", 7000), 
			new Product(103, "Iphone", 7000)));
	*/
	
	//Method to return list of products
	public List<Product> getAllProducts() {
		return repo.findAll();
	}
	
	/*
	int index = 0; //initializing the product index to 0
	//Method to return the index of the product by using prodId #NOT NEEDED WITH JPA
	public int getProductIndex(int prodId) {
		for (int i=0; i< products.size(); i++) {
			if(products.get(i).getProdId() == prodId) {
				index = i;
			}
		}
		return index;
	}
	*/
	
	//Method to find a product corresponding to a given id
	public Product getProductById(int prodId) {
		/*use stream API - #NOT NEEDED WITH JPA
		return products.stream()
				.filter(product -> product.getProdId() == pid)
				.findFirst().orElse(new Product(0, "No Item Found",0)); */
		return repo.findById(prodId).orElse(null);
		
	}
	
	//Method to add a product
	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		//get the image and convert it into byte format
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		//We retrieved the image details that came as part in the request and set it to the product fields. Now save the new product in the DB
		return repo.save(product);
	}
	
	//Method to update a product
	public Product updateProduct(int prodId, Product product, MultipartFile imageFile) throws IOException {
		product.setImageData(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repo.save(product);
	}
	//Method to delete a product by Id
	public void deleteProductById(int prodId) {
		repo.deleteById(prodId);
	}

	public List<Product> searchProducts(String keyword) {
		return repo.searchProducts(keyword);
	}

	
}
