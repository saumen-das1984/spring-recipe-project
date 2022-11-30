package com.spring.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import com.spring.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
