package com.manisha.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manisha.ecommerce.model.Product;
import com.manisha.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
//@CrossOrigin //allows the backend data to get populated on the frontend UI
public class ProductController {
	
	//Controller will get the product data from service file. Therefore, create a reference object of type ProductService
	private ProductService productService;
	
	//Using constructor injection to inject productService Bean
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	//Accepting request to return all the products. By using ResponseEntity as return type we also want to send the response status.
	//@RequestMapping(value="/products", method=RequestMethod.GET)
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(){
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

	//@RequestMapping("/products/{prodId}")
	@GetMapping("/product/{prodId}")
	public ResponseEntity<Product> getProduct(@PathVariable int prodId) {
		Product product = productService.getProductById(prodId);
		if(product != null){
			return new ResponseEntity<>(product, HttpStatus.OK);
			}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//@RequestMapping(value="/products", method=RequestMethod.POST)
	@PostMapping("/product") //? in the ResponseEntity indicates that we might return the data the response status
	public ResponseEntity<?> addProduct(@RequestPart Product product, 
										@RequestPart MultipartFile imageFile) {
		/*We are sending an image as well which is of different format so instead of @RequestBody we will use @RequestPart.
		 * @RequestBody - accepts the whole object/ accepts whole JSON as object
		 * @RequestPart - accepts data in multiple parts
		*/
		try {
			Product newProduct = productService.addProduct(product, imageFile);
			return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	//Method to return the image 
	@GetMapping("/product/{prodId}/image")
	public ResponseEntity<byte[]> getImageByProductID(@PathVariable int prodId){
		Product product = productService.getProductById(prodId);
		byte[] imageFile = product.getImageData();
		return ResponseEntity.ok()
				.contentType(MediaType.valueOf(product.getImageType()))
				.body(imageFile);
	}
	
	@PutMapping("/product/{prodId}")
	public ResponseEntity<String> updateProduct(@PathVariable int prodId, @RequestPart Product product, 
												@RequestPart MultipartFile imageFile) {
		Product updatedProduct = null;
		try {
			updatedProduct = productService.updateProduct(prodId, product, imageFile);
		} catch (IOException e) {
			
			return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
		}
		if (updatedProduct != null) {
			return new ResponseEntity<>("Updated", HttpStatus.OK);
		}
		else 
			return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{prodId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int prodId) {
		//check if product with given id is present or not
		Product product = productService.getProductById(prodId);
		if (product != null) {
			productService.deleteProductById(prodId);
			return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Product not Found", HttpStatus.NOT_FOUND);
		
	}
	
	//method which accepts some keywords from the clients and returns results matching with the keyword #Use @PathVariable to test and set URL "/products/search/{keyword}"
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		System.out.println("Searching with..."+ keyword);
		List<Product> products = productService.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
