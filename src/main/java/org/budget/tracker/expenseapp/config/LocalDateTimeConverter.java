package org.budget.tracker.expenseapp.config;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements PropertyValueConverter {


    @Override
    public Object write(Object value) {
        return value.toString();
    }

    @Override
    public LocalDateTime read(Object value) {

        String valueString = value.toString();

        if(valueString != null){
            String datetime = valueString.split("\\.")[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(datetime, formatter);
        }

        return null;
    }
}