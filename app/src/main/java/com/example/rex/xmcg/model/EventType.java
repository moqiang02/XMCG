package com.example.rex.xmcg.model;

import java.util.Date;

/**
 * Created by Rex on 2016/10/17.
 */

public class EventType {
    public static class CalendarEvent {
        public Date date;

        public CalendarEvent(Date date) {
            this.date = date;
        }
    }
}
