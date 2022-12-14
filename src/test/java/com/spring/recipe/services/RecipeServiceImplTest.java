package com.spring.recipe.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void testGetRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
		when(recipeRepository.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
	}
	
	private Set<Recipe> buildRecipe()
	{
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        return recipesData;
	}

}
