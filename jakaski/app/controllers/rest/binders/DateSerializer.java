package controllers.rest.binders;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.Resources;

public class DateSerializer implements JsonSerializer<Date> {

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext contex) {
        SimpleDateFormat formatter = new SimpleDateFormat(Resources.DEFAULT_DATE_FORMAT);
        return (date == null) ? null : new JsonPrimitive(formatter.format(date));
    }

}
