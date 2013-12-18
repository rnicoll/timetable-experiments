package org.lostics.timetabling.model;

import java.sql.Date;

/**
 * A template for a sequence of events, describing when they start and finish
 * running, and how often the recur.
 */
public class RecurringEvent {
    private final Module module;
    private int frequencyPerWeek;
    private Date start;
    private Date end;
    
    public      RecurringEvent(final Module setModule, final int setFrequency,
            final Date setStart, final Date setEnd) {
        assert setFrequency > 0;
        assert setFrequency < 7;
        
        this.module = setModule;
        this.frequencyPerWeek = setFrequency;
        this.start = setStart;
        this.end = setEnd;
    }

    /**
     * Get the number of times each week that this event is run. For example 1
     * would be once per week. This is the number of events that a student is
     * expected to attend, not the number of possible events a student could
     * attend.
     * 
     * @return the frequency of the event.
     */
    public int getFrequency() {
        return this.frequencyPerWeek;
    }

    /**
     * @return the module
     */
    public Module getModule() {
        return module;
    }

    /**
     * Get the earliest date on which this event could occur. Generally this would
     * be the Monday of the week when the event starts.
     * 
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Get the latest date on which this event could occur. Generally this would
     * be the Friday of the week when the event starts.
     * 
     * @return the start
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequencyPerWeek(int frequency) {
        this.frequencyPerWeek = frequency;
    }
}
