package com.spring.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.spring.recipe.command.IngredientCommand;
import com.spring.recipe.command.RecipeCommand;
import com.spring.recipe.services.IngredientService;
import com.spring.recipe.services.RecipeService;
import com.spring.recipe.services.UnitOfMeasureService;

public class IngredientControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
    UnitOfMeasureService unitOfMeasureService;
	
	IngredientController ingredientController;
	
	MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
		
	}

	@Test
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService,times(1)).findCommandById(anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdandIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testUpdateIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdandIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUOMs()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testSaveIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(2L);
		ingredientCommand.setId(3L);
		
		//when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
		
		//then
		mockMvc.perform(post("/recipe/2/ingredient")
					   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
					   .param("id", "")
					   .param("description", "some string"))
			   .andExpect(status().is3xxRedirection())
			   .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}

}
