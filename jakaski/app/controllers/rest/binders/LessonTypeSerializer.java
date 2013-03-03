package controllers.rest.binders;

import java.lang.reflect.Type;

import models.LessonType;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LessonTypeSerializer implements JsonSerializer<LessonType> {

    @Override
    public JsonElement serialize(LessonType lessonType, Type typeOf, JsonSerializationContext context) {
        return (lessonType == null) ? null : new JsonPrimitive(lessonType.toString().toLowerCase());
    }

}
