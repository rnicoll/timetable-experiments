package org.lostics.timetabling.model;

import java.util.HashMap;
import java.util.Map;


public class Schedule {
    private static final int DEFAULT_MUTATE_CHILDREN_COUNT = 6;
    
    private Map<RecurringEvent, EventSchedule> scheduledEvents = new HashMap<RecurringEvent, EventSchedule>();
    
    public EventSchedule getSchedule(final RecurringEvent event) throws UnscheduledEventException {
        final EventSchedule schedule = this.scheduledEvents.get(event);
        
        if (null == schedule) {
            throw new UnscheduledEventException(event);
        }
        
        return schedule;
    }
    
    public void scheduleEvent(final RecurringEvent event, final EventSchedule schedule) {
        this.scheduledEvents.put(event, schedule);
    }
    
    public static Schedule[] mutate(final Schedule a, final Schedule b) {
        return Schedule.mutate(a, b, DEFAULT_MUTATE_CHILDREN_COUNT);
    }
    
    public static Schedule[] mutate(final Schedule a, final Schedule b, final int children) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
