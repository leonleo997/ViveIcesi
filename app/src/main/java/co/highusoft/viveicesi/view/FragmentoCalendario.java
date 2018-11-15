package co.highusoft.viveicesi.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import co.highusoft.viveicesi.R;



public class FragmentoCalendario extends FragmentActivity  {

    private CalendarView calendarView;
    private TextView tv_mes;
    private ListView lv_agenda;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragmento_calendario);
        calendarView=findViewById(R.id.cv_calendar);
        tv_mes=findViewById(R.id.tv_mes);
        lv_agenda=findViewById(R.id.lv_agenda);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
    }



}
