package com.example.rex.xmcg.weiget.calendar;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;

    public EventDecorator(Activity context,int draw, Collection<CalendarDay> dates) {
//        this.color = color;
        this.dates = new HashSet<>(dates);
        drawable = context.getResources().getDrawable(draw);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new DotSpan(5, color));

        view.setBackgroundDrawable(drawable);
    }
}
