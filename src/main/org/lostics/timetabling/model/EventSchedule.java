package org.lostics.timetabling.model;

import java.util.Calendar;

/**
 * Represents a schedule to which an event can run. Association of the schedule
 * to event(s) is handled outside this class.
 */
public class EventSchedule {
    private int hourOfDay;
    private int dayOfWeek;
    
    public          EventSchedule(final int setHourOfDay, final int setDayOfWeek) {
        assert setHourOfDay >= 0;
        assert setHourOfDay < 24;
        
        assert setDayOfWeek >= Calendar.SUNDAY;
        assert setDayOfWeek <= Calendar.SATURDAY;
        
        this.hourOfDay = setHourOfDay;
        this.dayOfWeek = setDayOfWeek;
    }

    /**
     * @return the dayOfWeek
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @return the hourOfDay
     */
    public int getHourOfDay() {
        return hourOfDay;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @param hourOfDay the hourOfDay to set
     */
    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }
}
