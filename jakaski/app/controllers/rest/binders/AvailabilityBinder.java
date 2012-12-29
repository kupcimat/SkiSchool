package controllers.rest.binders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import models.rest.AvailabilityWrapper;
import play.Logger;
import play.data.binding.Global;
import play.data.binding.TypeBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controllers.rest.Resources;

@Global
public class AvailabilityBinder implements TypeBinder<AvailabilityWrapper> {

    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Object result = null;

        try {
            // TODO check json structure
            Gson gson = new GsonBuilder().setDateFormat(Resources.DEFAULT_DATE_FORMAT).create();
            result = gson.fromJson(value, AvailabilityWrapper.class);
        } catch (Exception e) {
            Logger.info(e, "Error occured while parsing JSON request = %s", value);
        }

        return result;
    }

}
