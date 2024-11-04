package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Categor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Categor,Long> {

    public Categor findByName(String name);

    @Query("Select c from Categor c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
    public Categor findByNameAndParant(@Param("name") String name,
                                       @Param("parantCategoryName") String parentCategoryName);
}
