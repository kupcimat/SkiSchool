package controllers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import play.data.binding.Global;
import play.data.binding.TypeBinder;

import com.google.gson.Gson;

@Global
public class SchedulerEventBinder implements TypeBinder<SchedulerEvent> {

    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Object result = null;

        try {
            Gson gson = new Gson();
            // TODO check json structure
            result = gson.fromJson(value, SchedulerEvent.class);

        } catch (Exception e) {
            // TODO log exception
        }

        return result;
    }

}
