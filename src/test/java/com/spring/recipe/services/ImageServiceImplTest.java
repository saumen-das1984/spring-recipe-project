package com.spring.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;

public class ImageServiceImplTest {
	
	ImageService imageService;
	
	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void testSaveImageFile() throws Exception {
		//given
		Long id = 1L;
		MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());
		
		Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        
        //when
        imageService.saveImageFile(id, multipartFile);
        
        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
