package com.example.rex.xmcg.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rex on 2016/10/17.
 */

public class EventType implements Serializable {
    public static class CalendarEvent {
        public Date date;

        public CalendarEvent(Date date) {
            this.date = date;
        }
    }

    public static class Frag {
    }

    public static class ToLogin {
    }

}
