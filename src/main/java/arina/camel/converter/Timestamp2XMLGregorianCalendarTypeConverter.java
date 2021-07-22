package arina.camel.converter;

import org.apache.camel.Converter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

@Converter
public class Timestamp2XMLGregorianCalendarTypeConverter
{
    @Converter
    public static XMLGregorianCalendar convert(Timestamp ts)
    {
        if(ts == null)
            return null;

        try
        {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(ts.getTime());
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }
        catch (DatatypeConfigurationException ignore)
        {
            return null;
        }
    }
}
