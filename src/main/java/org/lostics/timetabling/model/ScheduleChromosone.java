package org.lostics.timetabling.model;

/**
 * Represents a schedule against which events can run.
 */
public class ScheduleChromosone implements Comparable<ScheduleChromosone> {
    private final EventSchedule[] scheduledEvents;
    private Integer fitnessScore = null;
    
    public      ScheduleChromosone(final EventSchedule[] setSchedule) {
        this.scheduledEvents = setSchedule;
        this.calculateFitnessScore();
    }

    @Override
    public int compareTo(ScheduleChromosone other) {
        if (null == this.getFitnessScore()
                || null == other.getFitnessScore()) {
            return compareToSchedule(other);
        }
        
        int fitnessMatch = this.getFitnessScore().compareTo(other.getFitnessScore());
        
        if (fitnessMatch == 0) {
            return compareToSchedule(other);
        }
        
        return fitnessMatch;
    }

    /**
     * Compare this schedule against another based on the scheduled events. Used
     * where fitness scores are not available or are equal.
     * 
     * @param other
     * @return 
     */
    private int compareToSchedule(ScheduleChromosone other) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the fitnessScore
     */
    public Integer getFitnessScore() {
        return fitnessScore;
    }

    /**
     * @param fitnessScore the fitnessScore to set
     */
    public void setFitnessScore(Integer fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    /**
     * @return the scheduledEvents
     */
    public EventSchedule[] getScheduledEvents() {
        return scheduledEvents;
    }

    private void calculateFitnessScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int length() {
        return this.scheduledEvents.length;
    }

    public ScheduleChromosone crossover(ScheduleChromosone chromosoneB, int crossoverPoint) {
        assert this.scheduledEvents.length == chromosoneB.scheduledEvents.length;
        
        final EventSchedule[] newEvents = new EventSchedule[this.scheduledEvents.length];
        
        for (int sourceIdx = 0; sourceIdx < newEvents.length; sourceIdx++) {
            if (sourceIdx < crossoverPoint) {
                newEvents[sourceIdx] = this.scheduledEvents[sourceIdx];
            } else {
                newEvents[sourceIdx] = chromosoneB.scheduledEvents[sourceIdx];
            }
        }
        
        return new ScheduleChromosone(newEvents);
    }

    public void mutate(double mutationRate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
