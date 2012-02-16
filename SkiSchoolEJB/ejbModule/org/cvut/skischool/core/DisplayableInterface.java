package org.cvut.skischool.core;

import java.util.Date;

/**
 *
 * @author matej
 */
public interface DisplayableInterface {

    public Date startTime();

    public Date endTime();

    public String color();

    public String title();

    public boolean isAvailability();

    public boolean isLesson();
}
