package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;


//to make apis of product uplaod on swagger as well first service then repositry
public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productid) throws ProductException;

    public Product updateProduct(Long productid,Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

//    type page
    public Page<Product> getAllProduct(String category,List<String> colors,List<String> sizes,Integer minPrice
            ,Integer maxPrice,Integer minDisc,String sort,String stock,Integer pageNumber,Integer pageSize);


}
