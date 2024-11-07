package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Categor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Categor,Long> {

    public Categor findByName(String name);

    @Query("SELECT c FROM Categor c WHERE c.name = :name AND c.parentCategory.name = :parentCategoryName")
    public Categor findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);

}
