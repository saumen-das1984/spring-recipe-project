package com.spring.recipe.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {

		log.debug("Received a file");
		try {
			
			Recipe recipe = recipeRepository.findById(recipeId).get();
			
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			int byteCount =  0;
			for (byte b : file.getBytes()) {
				byteObjects[byteCount++] = b;
			}
			
			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			log.error("error occured during persisting imagefile in database - ", e);
			e.printStackTrace();
		}

	}

}
