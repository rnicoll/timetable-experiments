package org.lostics.timetabling.model;

import java.util.List;

/**
 * A module of a degree course, typically a taught element.
 * 
 * Used to group together staff and students involved in the taught element,
 * for example to allocate students to lectures.
 */
public class Module {
    private final String moduleCode;
    private List<Person> students;
    private List<Person> staff;
    private List<RecurringEvent> recurringEvents;
    
    public          Module(final String setModuleCode) {
        this.moduleCode = setModuleCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Module)) {
            return false;
        }
        
        final Module other = (Module)o;
        
        return this.getUsername().equals(other.getUsername());
    }
    
    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }
    
    @Override
    public String toString() {
        return this.moduleCode;
    }

    /**
     * @return the moduleCode
     */
    public String getUsername() {
        return moduleCode;
    }

    /**
     * @return the staff
     */
    public List<Person> getStaff() {
        return staff;
    }

    /**
     * @return the students
     */
    public List<Person> getStudents() {
        return students;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(List<Person> staff) {
        this.staff = staff;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Person> students) {
        this.students = students;
    }

    /**
     * @return the recurringEvents
     */
    public List<RecurringEvent> getRecurringEvents() {
        return recurringEvents;
    }

    /**
     * @param recurringEvents the recurringEvents to set
     */
    public void setRecurringEvents(List<RecurringEvent> recurringEvents) {
        this.recurringEvents = recurringEvents;
    }
    
    
}
