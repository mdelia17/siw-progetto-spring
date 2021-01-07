package it.uniroma3.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate,Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate localdate) {
		if (localdate != null) {
			return Date.valueOf(localdate);
		}
		return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbdate) {
		if (dbdate != null) {
			return dbdate.toLocalDate();
		}
		return null;
	}

}