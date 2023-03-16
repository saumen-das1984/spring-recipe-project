package com.spring.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.spring.recipe.command.UnitOfMeasureCommand;
import com.spring.recipe.domain.UnitOfMeasure;

import lombok.Synchronized;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Override
	@Synchronized
    @Nullable
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if (source == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
	}

}
