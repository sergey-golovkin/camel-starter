package arina.camel.converter;

import org.apache.camel.Converter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Converter
public class OffsetDateTime2TimestampTypeConverter
{
    @Converter
    public static Timestamp convert(OffsetDateTime value)
    {
        try
        {
            return Timestamp.from(value.toInstant());
        }
        catch (Exception ignore)
        {
            return null;
        }
    }
}
