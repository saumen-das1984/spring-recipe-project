package com.spring.recipe.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.recipe.command.IngredientCommand;
import com.spring.recipe.converters.IngredientToIngredientCommand;
import com.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring.recipe.domain.Ingredient;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	@Mock
	RecipeRepository recipeRepository;

	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
	}

	@Test
	public void testFindByRecipeIdandIngredientId() throws Exception {
		
	}
	
	@Test
	public void testFindByRecipeIdandIngredientIdHappyPath() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdandIngredientId(1L, 3L);
		
		//when
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository,times(1)).findById(anyLong());
	}

}
