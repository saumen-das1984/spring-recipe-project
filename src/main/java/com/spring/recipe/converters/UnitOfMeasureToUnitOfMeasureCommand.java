package com.spring.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.spring.recipe.command.UnitOfMeasureCommand;
import com.spring.recipe.domain.UnitOfMeasure;

import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>{

	@Override
	@Synchronized
    @Nullable
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
            uomc.setId(source.getId());
            uomc.setDescription(source.getDescription());
            return uomc;
        }
        return null;
	}
}
