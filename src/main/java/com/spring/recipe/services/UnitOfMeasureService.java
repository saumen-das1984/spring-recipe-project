package com.spring.recipe.services;

import java.util.Set;

import com.spring.recipe.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	
	public Set<UnitOfMeasureCommand> listAllUOMs();

}
