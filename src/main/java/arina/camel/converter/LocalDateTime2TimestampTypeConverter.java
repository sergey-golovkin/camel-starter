package arina.camel.converter;

import org.apache.camel.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Converter
public class LocalDateTime2TimestampTypeConverter
{
    @Converter
    public static Timestamp convert(LocalDateTime value)
    {
        try
        {
            return Timestamp.from(value.toInstant(ZoneOffset.of(ZoneOffset.systemDefault().getId())));
        }
        catch (Exception ignore)
        {
            return null;
        }
    }
}
