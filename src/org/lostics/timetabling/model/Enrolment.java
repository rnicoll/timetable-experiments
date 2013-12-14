package org.lostics.timetabling.model;

/**
 *
 * @author jrn
 */
public class Enrolment {
    private final Module module;
    private final Person person;
    
    public          Enrolment(final Module setModule, final Person setPerson) {
        this.module = setModule;
        this.person = setPerson;
    }

    /**
     * @return the module
     */
    public Module getModule() {
        return module;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }
}
