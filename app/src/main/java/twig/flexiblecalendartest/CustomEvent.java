package twig.flexiblecalendartest;

import com.p_v.flexiblecalendar.entity.Event;

import java.util.Date;

/**
 * Created by twig on 5/03/2016.
 */
public class CustomEvent implements Event {
    public String title;
    public int dayOfMonth;

    public CustomEvent(String title, int dayOfMonth) {
        this.title = title;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public int getColor() {
        return R.color.colorPrimary;
    }
}
