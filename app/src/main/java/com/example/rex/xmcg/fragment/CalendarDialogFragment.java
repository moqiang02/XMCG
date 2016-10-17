package com.example.rex.xmcg.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.EventType;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Rex on 2016/10/17.
 */

public class CalendarDialogFragment extends AppCompatDialogFragment implements OnDateSelectedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private int curr_month, next_month, start_day, end_day;

    public CalendarDialogFragment(int curr_month, int next_month, int start_day, int end_day) {
        this.curr_month = curr_month;
        this.end_day = end_day;
        this.next_month = next_month;
        this.start_day = start_day;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //inflate custom layout and get views
        //pass null as parent view because will be in dialog layout
        View view = inflater.inflate(R.layout.dialog_basic, null);


        MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), curr_month, start_day);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), next_month, end_day);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        widget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();
        widget.setOnDateChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//        textView.setText(FORMATTER.format(date.getDate()));
        EventBus.getDefault().post(new EventType.CalendarEvent(date.getDate()));
        this.dismiss();
    }


}
