package twig.flexiblecalendartest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.MonthViewPagerAdapter;
import com.p_v.flexiblecalendar.entity.Event;

import org.joor.Reflect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    @Bind(R.id.eventsCalendar)
    protected FlexibleCalendarView calendar;

    protected List<CustomEvent> eventsCache;


    public MainActivityFragment() {
        setRetainInstance(true);
        eventsCache = new ArrayList<CustomEvent>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, getActivity());

        calendar.setEventDataProvider(new EventsDataProvider());
    }


    @OnClick(R.id.btnAddEvent)
    protected void onBtnAddEventClick() {
        Calendar cal = Calendar.getInstance();
        int whichDay = 1 + ((int) (cal.getActualMaximum(Calendar.DAY_OF_MONTH) * Math.random()));

        Log.e("btnAddEvent", String.valueOf(whichDay));

        CustomEvent newEvent = new CustomEvent("Event #" + String.valueOf((eventsCache.size() +1)), whichDay);
        eventsCache.add(newEvent);
        calendar.refresh(); // This does nothing
    }


    @OnClick(R.id.btnForceRefresh)
    protected void onBtnForceRefreshClick() {
//        List<FlexibleCalendarGridAdapter> adapters = calendar.monthViewPagerAdapter.dateAdapters;
        MonthViewPagerAdapter monthViewPagerAdapter = Reflect.on(calendar).get("monthViewPagerAdapter");
        List<Object> adapters = (List<Object>) Reflect.on(monthViewPagerAdapter).get("dateAdapters");

        for (Object adapter : adapters) {
//            adapter.notifyDataSetChanged();
            Reflect.on(adapter).call("notifyDataSetChanged");
        }

        // Calendar now refreshes
    }



    protected class EventsDataProvider implements FlexibleCalendarView.EventDataProvider {
        @Override
        public List<? extends Event> getEventsForTheDay(int year, int month, int day) {
            List<Event> events = new ArrayList<>();

            if (eventsCache != null) {
                for (CustomEvent e : eventsCache) {
                    if (e.dayOfMonth == day) {
                        events.add(e);
                    }
                }
            }

            return events;
        }
    }
}
