package org.lostics.timetabling.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;


public class Population {
    public static final int DEFAULT_POPULATION_SIZE = 6;
    public static final float DEFAULT_CROSSOVER_RATE = 0.7f;
    public static final float DEFAULT_MUTATION_RATE = 0.001f;
    
    private final Random random = new Random();
    private ScheduleChromosone[] chromosones;
    private double crossoverRate = DEFAULT_CROSSOVER_RATE;
    private double mutationRate = DEFAULT_MUTATION_RATE;
    
    public              Population(final ScheduleChromosone[] setChromosones) {
        this.chromosones = setChromosones;
        
        this.random.setSeed(System.currentTimeMillis());
    }
    
    public static Population generate(final Collection<Module> modules) {
        throw new UnsupportedOperationException();
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
            double fitnessA = this.random.nextDouble() * fitnessTotal;
            double fitnessB = this.random.nextDouble() * fitnessTotal;
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
        
        if (this.random.nextFloat() < this.crossoverRate) {
            final int crossoverPoint = this.random.nextInt(chromosoneA.length());
            newChromosone = chromosoneA.crossover(chromosoneB, crossoverPoint);
        } else {
            newChromosone = new ScheduleChromosone(chromosoneA.getScheduledEvents());
        }
        
        newChromosone.mutate(this.mutationRate);
        
        return newChromosone;
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
}
