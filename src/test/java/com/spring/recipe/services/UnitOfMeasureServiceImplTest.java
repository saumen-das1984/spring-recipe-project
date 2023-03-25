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

import com.spring.recipe.command.UnitOfMeasureCommand;
import com.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring.recipe.domain.UnitOfMeasure;
import com.spring.recipe.repositories.UnitOfMeasureRepository;

public class UnitOfMeasureServiceImplTest {
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	UnitOfMeasureService unitOfMeasureService;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void testListAllUOMs() {
		//given
		Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		
		unitOfMeasureSet.add(uom1);
		unitOfMeasureSet.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);
		
		//when
		Set<UnitOfMeasureCommand> unitOfMeasureCommandsSet = unitOfMeasureService.listAllUOMs();
		
		//then
		assertEquals(2, unitOfMeasureCommandsSet.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
