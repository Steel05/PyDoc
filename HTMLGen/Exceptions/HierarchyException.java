package Exceptions;

import HTML.Components.Component;

/**
 * Represents an error in the heirarchy of an HTML document. This happens when a component that is already
 * the child of another component, attempts to become the child of a different component.
 */
public class HierarchyException extends Exception {
    private Component attemptedChild;

    public HierarchyException(Component attemptedChild){
        this.attemptedChild = attemptedChild; 
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();

        System.err.printf("Cannot child %s because it is already a child of %s", 
            attemptedChild.toString(), 
            attemptedChild.getParent().toString());
        System.err.println("Call the child's .release() in order to release it from its parent.");
    }
}
