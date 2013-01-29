package controllers.rest.binders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import models.rest.StudentWrapper;
import play.Logger;
import play.data.binding.Global;
import play.data.binding.TypeBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Global
public class StudentBinder implements TypeBinder<StudentWrapper> {

    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Object result = null;

        try {
            // TODO check json structure
            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(value, StudentWrapper.class);
        } catch (Exception e) {
            Logger.info(e, "Error occured while parsing JSON request = %s", value);
        }

        return result;
    }

}
