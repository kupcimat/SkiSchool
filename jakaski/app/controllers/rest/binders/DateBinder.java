package controllers.rest.binders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import play.Logger;
import play.data.binding.TypeBinder;
import controllers.rest.Resources;

public class DateBinder implements TypeBinder<Date> {

    @Override
    public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
        Object result = null;

        try {
            result = DateUtils.parseDate(value, new String[] { Resources.DEFAULT_SHORT_DATE_FORMAT });
        } catch (Exception e) {
            Logger.info(e, "Error occured while parsing date = %s", value);
        }

        return result;
    }

}
