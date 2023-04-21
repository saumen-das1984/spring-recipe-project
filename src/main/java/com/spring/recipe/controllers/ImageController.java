package com.spring.recipe.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.recipe.command.RecipeCommand;
import com.spring.recipe.services.ImageService;
import com.spring.recipe.services.RecipeService;

@Controller
public class ImageController {
	
	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("recipe/{id}/image")
	public String showUploadImageForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(Long.valueOf(id), file);
		return "redirect:/recipe/"+id+"/show";
	}
	
	@GetMapping("recipe/{id}/recipeimage")
	public void renderImagefromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
		
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
		
		byte[] byteArray = new byte[null!=recipeCommand.getImage() ? recipeCommand.getImage().length : 0];
		
		int byteCount = 0;
		if(null!=recipeCommand.getImage()) {
			for (Byte wrappedByte : recipeCommand.getImage()) {
				byteArray[byteCount++] = wrappedByte;
			}
		}

		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(byteArray);
		IOUtils.copy(is, response.getOutputStream());
	}
}
