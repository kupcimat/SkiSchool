package controllers.scheduler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import play.data.binding.Global;
import play.data.binding.TypeBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Global
public class SchedulerEventBinder implements TypeBinder<SchedulerEvent> {

    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Object result = null;

        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
            // TODO check json structure
            System.err.println("created event: " + gson.fromJson(value, SchedulerEvent.class).toString());
            result = gson.fromJson(value, SchedulerEvent.class);

        } catch (Exception e) {
            // TODO log exception
            System.err.println("JSON parsing error");
        }

        return result;
    }

}
