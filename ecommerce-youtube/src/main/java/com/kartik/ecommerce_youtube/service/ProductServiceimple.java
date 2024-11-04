package com.kartik.ecommerce_youtube.service;


import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Categor;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.repository.CategoryRepository;
import com.kartik.ecommerce_youtube.repository.ProductRepository;
import com.kartik.ecommerce_youtube.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Categor secondLevel=categoryRepository.findByNameAndParant(req.getSecondLevelCategory(), topLevel.getName());

        if(secondLevel==null){
            Categor secondLevelCategry=new Categor();
            secondLevelCategry.setName(req.getSecondLevelCategory());
            secondLevelCategry.setParentCategory(topLevel);
            secondLevelCategry.setLevel(2);

            secondLevel=categoryRepository.save(secondLevelCategry);

        }

        Categor thirdLevel=categoryRepository.findByNameAndParant(req.getThirdLevelCategory(),secondLevel.getName());

        if(thirdLevel==null){
            Categor thirdLevelCategry=new Categor();
            thirdLevelCategry.setName(req.getThirdLevelCategory());
            thirdLevelCategry.setParentCategory(secondLevel);
            thirdLevelCategry.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLevelCategry);

        }



        return null;
    }

    @Override
    public String deleteProduct(Long productid) throws ProductException {
        return "";
    }

    @Override
    public Product updateProduct(Long productid, Product product) throws ProductException {
        return null;
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return null;
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDisc, String sort, String stock, Integer pageNumber, Integer pageSize) {
        return null;
    }
}
