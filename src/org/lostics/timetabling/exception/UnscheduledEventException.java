package org.lostics.timetabling.exception;

import org.lostics.timetabling.model.RecurringEvent;

/**
 * Indicates that a failure in scheduling has left an event without a schedule.
 * This should never happen, and is essentially a data inconsistency issue,
 * only likely to arise if something interrupts the scheduling process, or if a
 * schedule is read while being generated.
 */
public class UnscheduledEventException extends Exception {

    public UnscheduledEventException(RecurringEvent event) {
        super("The event "
            + event + " has not been scheduled.");
    }

}
