package it.uniroma3.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time>{

	@Override
	public Time convertToDatabaseColumn(LocalTime localtime) {
		if (localtime != null) {
			return Time.valueOf(localtime);
		}
		return null;
	}

	@Override
	public LocalTime convertToEntityAttribute(Time dbtime) {
		if (dbtime != null) {
			return dbtime.toLocalTime();
		}
		return null;
	}
}
