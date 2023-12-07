package HTML.Components;

import java.util.ArrayList;
import java.util.HashMap;

import CSS.CSSClass;
import Exceptions.HierarchyException;
import HTML.ComponentChecker;
import Interfaces.Writable;

public abstract class Component implements Writable {
    private Component parent = null;
    private ArrayList<Component> children = new ArrayList<>();

    private final String tag;
    protected int childId;
    private HashMap<String, CSSClass> cssClasses = new HashMap<>();

    protected Component(String tag){
        this.tag = tag;

        ComponentChecker.registerComponent(this);
    }

    /**
     * Gets the parent of this component.
     * @return The parent component
     */
    public final Component getParent(){
        return parent;
    }
    /**
     * Checks if the component has a parent component.
     * @return Whether or not this component is a child
     */
    public boolean hasParent(){
        return parent != null;
    }
    /**
     * Registers this component to be a child of another component.
     * <p>NOTE: If this component already has a parent, a stack trace will be printed to the
     * console and the parent component of this component will not be changed.
     * @param parent The component for this component to become the child of
     */
    protected final void registerParent(Component parent, int id){
        try{
            if (this.parent != null){
                throw new HierarchyException(this);
            }
            this.parent = parent;
            this.childId = id;
        }
        catch(HierarchyException e){
            e.printStackTrace();
        }
    }

    /**
     * Releases this component from its parent, making it a "free component". If this component
     * is already free, this function does nothing.
     * <p>If left freed, this component will get flagged when the HTML file is saved.
     */
    public void release(){
        if (parent == null){
            return;
        }
        parent.removeChild(this.childId);
        parent = null;
    }

    /**
     * Adds a child to this component.
     * @param child The child component
     * @return This component to allow for call chaining
     */
    public Component addChild(Component child){
        children.add(child);
        childId = children.size() - 1;
        child.registerParent(this, children.size() - 1);

        return this;
    }
    /**
     * Adds all provided components to this component as children
     * @param children The components to add
     */
    public final Component addChildren(Component... children){
        for (Component comp : children){
            this.addChild(comp);
        }

        return this;
    }
    /**
     * Removes a child component.
     * @param id The id of the child to remove
     * @return The removed child component
     */
    public final Component removeChild(int id){
        return children.remove(id);
    }

    /**
     * Checks if the component has been assigned a CSS class.
     * @return True if this component has at least 1 assigned class
     */
    public final boolean assignedClass(){
        return cssClasses.size() != 0;
    }
    /**
     * Retrieves all the classes assigned to this component.
     * @return An array of all the classes assigned to this component
     */
    public final CSSClass[] getClasses(){
        return cssClasses.values().toArray(new CSSClass[cssClasses.size()]);
    }
    /**
     * Adds the classes to the component.
     * @param cssClasses The classes to add the component
     * @return This component so that inlining this statement is valid
     */
    public final Component addCSSClasses(CSSClass... cssClasses){
        for (CSSClass cclass : cssClasses){
            this.cssClasses.put(cclass.getName(), cclass);
        }
        return this;
    }

    /**
     * Gets the associated tag for this component.
     * @return This component's tag
     */
    public String getTag(){
        return tag;
    }
    /**
     * Gets all the child components of this component.
     * @return All child components
     */
    public Component[] getChildren(){
        return children.toArray(new Component[children.size()]);
    }

    /**
     * Constructs the {@code class="..."} decleration of the HTML tag. 
     * <p>If this component has no assigned class, this function will return an empty string.
     * <p>If this component has at least one assigned class, it will return in format {@code ' class="..."''},
     * with the a space fixed to the beginning of the string.
     * @return The appropriate class modifier
     */
    protected final String constructCSSClassString(){
        if (!this.assignedClass()){
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(" class=\"");
        for (CSSClass cclass : cssClasses.values()){
            builder.append(cclass.getName() + " ");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append("\"");
        return builder.toString();
    }

    /**
     * Converts this component into valid HTML code.
     * @return This component as HTML code
     */
    public String write(){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<%s%s>\n", tag, constructCSSClassString()));
        for (Component child : children){
            builder.append(child.write() + "\n");
        }
        builder.append(String.format("</%s>", tag));
        return builder.toString();
    }

    @Override
    public String toString(){
        return String.format("<%s> Component", tag);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Component){
            Component other = (Component)o;
            
            return (this.childId == other.childId) && (tag.equals(other.getTag())) && (parent.childId == other.getParent().childId);
        }
        return false;
    }
}
