package arina.camel.utils;

import org.apache.camel.Exchange;

public class CamelHeaders
{
    public static String simpleName(String name)
    {
        return "${header." + name + "}";
    }

    public static <T> T getValue(Exchange exchange, String name, Class<T> clazz)
    {
        return exchange.getIn().getHeader(name, clazz);
    }

    public static void setValue(Exchange exchange, String name, Object value)
    {
        exchange.getIn().setHeader(name, value);
    }
}
