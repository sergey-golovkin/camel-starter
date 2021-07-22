package arina.camel.converter;

import org.apache.camel.Converter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;

@Converter
public class XMLGregorianCalendar2TimestampTypeConverter
{
    @Converter
	public static Timestamp convert(XMLGregorianCalendar xmlgc)
	{
		if(xmlgc == null)
			return null;

		return new Timestamp(xmlgc.toGregorianCalendar().getTimeInMillis());
	}
}
