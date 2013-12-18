package org.lostics.timetabling.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Population {
    public static final int DEFAULT_POPULATION_SIZE = 6;
    public static final float DEFAULT_CROSSOVER_RATE = 0.7f;
    public static final float DEFAULT_MUTATION_RATE = 0.001f;
    
    private final Random random = new Random();
    private double crossoverRate = DEFAULT_CROSSOVER_RATE;
    private double mutationRate = DEFAULT_MUTATION_RATE;
    private List<Population.Partial> populations = new ArrayList<>();
    
    private                 Population() {
        this.random.setSeed(System.currentTimeMillis());
    }

    /**
     * Add a partial population of distinct events (events whose scheduling
     * cannot cause one person to have to be in two places at the same time)
     * to the list of populations.
     * 
     * @param distinctEvents an unordered set of distinct events.
     */
    private void addPartial(final RecurringEvent[] distinctEvents) {
        this.populations.add(new Partial(DEFAULT_POPULATION_SIZE, distinctEvents));
    }
    
    /**
     * Generate a population ready for mutation, by splitting it into distinct
     * event sets.
     *
     * @param modules a list of modules to extract events from.
     * @return a population. No initial schedules are generated at this point.
     */
    public static Population generate(final Collection<Module> modules) {
        final Map<Person, Set<RecurringEvent>> eventLookup = generateEventLookup(modules);
        final List<Set<RecurringEvent>> distinctEvents = new ArrayList<>();
        final Population population = new Population();
        
        // Sort events into distinct sets with no overlapping students/staff
        for (Module module: modules) {
            final List<Set<RecurringEvent>> candidateSets = getCandidateSets(distinctEvents, module, eventLookup);
            
            for (RecurringEvent event: module.getRecurringEvents()) {
                if (candidateSets.isEmpty()) {
                    final Set<RecurringEvent> newSet = new HashSet<>();
                    
                    newSet.add(event);
                    
                    distinctEvents.add(newSet);
                } else {
                    for (Set<RecurringEvent> eventSet: candidateSets) {
                        eventSet.add(event);
                        break;
                    }
                }
            }
        }
        
        // Push the partial populations into the overall population
        for (Set<RecurringEvent> events: distinctEvents) {
            population.addPartial(events.toArray(new RecurringEvent[0]));
        }
        
        return population;
    }

    /**
     * Get the list of potential event sets that the given module's events can be
     * added into.
     * 
     * @param distinctEvents
     * @param module
     * @param eventLookup
     * @return 
     */
    private static List<Set<RecurringEvent>> getCandidateSets(final List<Set<RecurringEvent>> distinctEvents,
            final Module module, final Map<Person, Set<RecurringEvent>> eventLookup) {
        final List<Set<RecurringEvent>> candidateSets = new ArrayList<>(distinctEvents);
        for (Person person: module.getUsers()) {
            final Set<RecurringEvent> collisionEvents = eventLookup.get(person);
            final Iterator<Set<RecurringEvent>> candidateIterator = candidateSets.iterator();
            
            while (candidateIterator.hasNext()) {
                final Set<RecurringEvent> candidateSet = candidateIterator.next();
                
                for (RecurringEvent collisionEvent: collisionEvents) {
                    if (candidateSet.contains(collisionEvent)) {
                        candidateIterator.remove();
                        break;
                    }
                }
            }
            
            // Abort searching for candidate sets if none remain
            if (candidateSets.isEmpty()) {
                break;
            }
        }
        return candidateSets;
    }

    private static Map<Person, Set<RecurringEvent>> generateEventLookup(final Collection<Module> modules) {
        final Map<Person, Set<RecurringEvent>> eventLookup = new HashMap<>();
        
        for (Module module: modules) {
            for (RecurringEvent event: module.getRecurringEvents()) {
                for (Person person: module.getUsers()) {
                    Set<RecurringEvent> events = eventLookup.get(person);
                    
                    if (null == events) {
                        events = new HashSet<>();
                        eventLookup.put(person, events);
                    }
                    events.add(event);
                }
            }
        }
        
        return eventLookup;
    }

    /**
     * @return the crossoverRate
     */
    public double getCrossoverRate() {
        return crossoverRate;
    }

    /**
     * @return the mutationRate
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * @param crossoverRate the crossoverRate to set
     */
    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    /**
     * @param mutationRate the mutationRate to set
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
    
    public class Partial extends Object {
        private ScheduleChromosone[] chromosones;
        private final RecurringEvent[] events;
        
        private      Partial(final int populationSize, final RecurringEvent[] setEvents) {
            this.chromosones = new ScheduleChromosone[populationSize];
            this.events = setEvents;
        }
    
        public void mutate() {
            Arrays.sort(this.chromosones);

            long fitnessTotal = 0;

            for (ScheduleChromosone chromosone: this.chromosones) {
                fitnessTotal += chromosone.getFitnessScore();
            }

            final ScheduleChromosone[] newChromosones = new ScheduleChromosone[chromosones.length];

            // Create a new population of chromosones
            for (int targetIdx = 0; targetIdx < newChromosones.length; targetIdx++) {
                double fitnessA = Population.this.random.nextDouble() * fitnessTotal;
                double fitnessB = Population.this.random.nextDouble() * fitnessTotal;
                ScheduleChromosone chromosoneA = null;
                ScheduleChromosone chromosoneB = null;

                for (int sourceIdx = 0; sourceIdx < this.chromosones.length; sourceIdx++) {
                    if (fitnessA > 0) {
                        fitnessA -= this.chromosones[sourceIdx].getFitnessScore();
                        if (fitnessA < 0) {
                            chromosoneA = this.chromosones[sourceIdx];
                        }
                    }
                    if (fitnessB > 0) {
                        fitnessB -= this.chromosones[sourceIdx].getFitnessScore();
                        if (fitnessB < 0) {
                            chromosoneB = this.chromosones[sourceIdx];
                        }
                    }

                    if (null != chromosoneA
                        && null != chromosoneB) {
                        break;
                    }
                }

                assert null != chromosoneA;
                assert null != chromosoneB;

                newChromosones[targetIdx] = mutate(chromosoneA, chromosoneB);
            }

            // Replace the existing population with the new one
            this.chromosones = newChromosones;
        }

        private ScheduleChromosone mutate(final ScheduleChromosone chromosoneA,
                final ScheduleChromosone chromosoneB) {
            final ScheduleChromosone newChromosone;

            if (Population.this.random.nextFloat() < Population.this.crossoverRate) {
                final int crossoverPoint = Population.this.random.nextInt(chromosoneA.length());
                newChromosone = chromosoneA.crossover(chromosoneB, crossoverPoint);
            } else {
                newChromosone = new ScheduleChromosone(chromosoneA.getScheduledEvents());
            }

            newChromosone.mutate(Population.this.mutationRate);

            return newChromosone;
        }
    }
}
