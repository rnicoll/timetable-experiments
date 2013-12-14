package org.lostics.timetabling.model;


public class UnscheduledEventException extends Exception {

    public UnscheduledEventException(RecurringEvent event) {
        super("The event "
            + event + " has not been scheduled.");
    }

}
