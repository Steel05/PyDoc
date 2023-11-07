package HTML;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import HTML.Components.Component;

/**
 * Manages all components
 */
public class ComponentChecker {
    private static ArrayList<Component> components = new ArrayList<>();

    /**
     * Registers a component with the checker.
     * @param component
     */
    public static void registerComponent(Component component){
        components.add(component);
    }

    /**
     * Checks for any components who have no parent.
     * @return If the user would like to continue with saving the file
     */
    public static ArrayList<Component> checkForFreeComponents(){
        ArrayList<Component> freeComponents = new ArrayList<>();

        for (Component component : components){
            if (!component.hasParent()){
                freeComponents.add(component);
            }
        }

        return freeComponents;
    }

    /**
     * Confirms if the file is clear to save 
     * @return
     */
    public static boolean clearToSave(){
        ArrayList<Component> free = checkForFreeComponents();

        if (free.isEmpty()){
            return true;
        }

        StringBuilder content = new StringBuilder();
        content.append(String.format("%d free component%s found:\n", free.size(), free.size() == 1 ? "" : "s"));
        for (Component comp : free){
            content.append(comp.getTag() + " component\n");
        }
        content.append("\nWould you like to save anyway?");

        int response = JOptionPane.showConfirmDialog(null, content.toString(), 
            "Free components found", JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }
}
