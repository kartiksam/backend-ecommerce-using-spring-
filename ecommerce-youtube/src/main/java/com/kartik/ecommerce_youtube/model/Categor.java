package com.kartik.ecommerce_youtube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Categor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max=50)
    private String name;

//    one parent category can have multi child category
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="parent_category_id")
    private Categor parentCategory;

private int level;

public Categor(){

}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(max = 50) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(max = 50) String name) {
        this.name = name;
    }

    public Categor getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Categor parentCategory) {
        this.parentCategory = parentCategory;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
