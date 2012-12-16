package controllers.rest.binders;

import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.Resources;

public class DateSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext contex) {
        String value = DateFormatUtils.format(date, Resources.DEFAULT_DATE_FORMAT);
        return (date == null) ? null : new JsonPrimitive(value);
    }

}
