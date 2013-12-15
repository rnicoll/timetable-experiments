package org.lostics.timetabling.model;

/**
 * A person (student, staff or otherwise).
 */
public class Person {
    private final String username;
    
    public          Person(final String setUsername) {
        this.username = setUsername;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        
        final Person other = (Person)o;
        
        return this.getUsername().equals(other.getUsername());
    }
    
    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }
    
    @Override
    public String toString() {
        return this.username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    
}
