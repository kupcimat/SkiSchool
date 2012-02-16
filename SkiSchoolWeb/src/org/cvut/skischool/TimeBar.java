package org.cvut.skischool;

import org.cvut.skischool.core.DisplayableInterface;

/**
 *
 * @author matej
 */
public class TimeBar {

    private int left;
    private int width;
    private DisplayableInterface content;

    public TimeBar(int left, int width, DisplayableInterface content) {
        this.left = left;
        this.width = width;
        this.content = content;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public DisplayableInterface getContent() {
        return content;
    }

    public void setContent(DisplayableInterface content) {
        this.content = content;
    }
}
