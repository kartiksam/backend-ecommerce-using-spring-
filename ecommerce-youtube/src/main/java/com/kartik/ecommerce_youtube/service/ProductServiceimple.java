package com.kartik.ecommerce_youtube.service;


import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Categor;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.repository.CategoryRepository;
import com.kartik.ecommerce_youtube.repository.ProductRepository;
import com.kartik.ecommerce_youtube.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//implemt prouct service interface and it will be service because here we are going to write all our business logic

public class ProductServiceimple implements ProductService{

//    instances
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserService userService;

    public ProductServiceimple(ProductRepository productRepository,
                               CategoryRepository categoryRepository, UserService userService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {

        Categor topLevel=categoryRepository.findByName(req.getTopLevelCategory());

        if(topLevel==null){
            Categor topLevelCategry=new Categor();
            topLevelCategry.setName(req.getTopLevelCategory());
            topLevelCategry.setLevel(1);

            topLevel=categoryRepository.save(topLevelCategry);

        }

        Categor secondLevel=categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());

        if(secondLevel==null){
            Categor secondLevelCategry=new Categor();
            secondLevelCategry.setName(req.getSecondLevelCategory());
            secondLevelCategry.setParentCategory(topLevel);
            secondLevelCategry.setLevel(2);

            secondLevel=categoryRepository.save(secondLevelCategry);

        }

        Categor thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());

        if(thirdLevel==null){
            Categor thirdLevelCategry=new Categor();
            thirdLevelCategry.setName(req.getThirdLevelCategory());
            thirdLevelCategry.setParentCategory(secondLevel);
            thirdLevelCategry.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLevelCategry);

        }

Product product=new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
product.setPrice(req.getPrice());
product.setSizes(req.getSize());
product.setQuantity(req.getQuantity());
product.setCategory(thirdLevel);
product.setCreatedAt(LocalDateTime.now());

//save the product
        Product savedProduct=productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productid) throws ProductException {
        Product product=findProductById(productid);
//        delete then all sizes will be clear
        product.getSizes().clear();
        productRepository.delete(product);
        return "product deleted succesfully";
    }

    @Override
    public Product updateProduct(Long productid, Product req) throws ProductException {
        Product product=findProductById(productid);
        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt=productRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }

throw new ProductException("Product not found with id "+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDisc, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable= PageRequest.of(pageNumber,pageSize);

//        all methods ina bovbe we have done query using auto jpa methods
//        but this method we have written query accv to our need

        List<Product> products=productRepository.filterProducts(category,minPrice,maxPrice,minDisc,sort);

//        if not empty
        if(!colors.isEmpty()){
            products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
if(stock!=null){
    if(stock.equals("in_stock")){
        products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
    }else if(stock.equals("out_of_stock")){
        products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());

    }
}
int si=(int) pageable.getOffset();
//1page =10products page no2 1page =10pro ei=pageno 1=11
        int ei=Math.min(si+pageable.getPageSize(),products.size());
        List<Product> pageContent=products.subList(si,ei);
        Page<Product> filteredProducts=new PageImpl<>(pageContent,pageable,products.size());

return filteredProducts;
    }
}
