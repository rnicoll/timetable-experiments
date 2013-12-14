package org.lostics.timetabling.model;

/**
 *
 * @author jrn
 */
public class Module {
    private final String moduleCode;
    
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
    
    
}
