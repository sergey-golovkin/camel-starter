package arina.camel.converter;

import arina.camel.converter.XMLGregorianCalendar2TimestampTypeConverter;
import org.apache.camel.Converter;

import javax.xml.datatype.DatatypeFactory;
import java.sql.Timestamp;

@Converter
public class String2TimestampTypeConverter
{
    @Converter
    public static Timestamp convert(String value)
    {
        try
        {
            return (value == null ? null : Timestamp.valueOf(value));
        }
        catch (Exception ignore)
        {
        }

        try
        {
            return XMLGregorianCalendar2TimestampTypeConverter.convert(DatatypeFactory.newInstance().newXMLGregorianCalendar(value));
        }
        catch (Exception ignore)
        {
            return null;
        }
    }
}
