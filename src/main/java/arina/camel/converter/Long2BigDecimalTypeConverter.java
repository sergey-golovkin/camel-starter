package arina.camel.converter;

import org.apache.camel.Converter;

import java.math.BigDecimal;

@Converter
public class Long2BigDecimalTypeConverter
{
    @Converter
    public static BigDecimal convert(Long value)
    {
        return (value == null ? null : new BigDecimal(value));
    }
}
