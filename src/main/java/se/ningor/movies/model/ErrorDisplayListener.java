package se.ningor.movies.model;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class ErrorDisplayListener implements SystemEventListener {
    
    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        UIComponent component = (UIComponent) event.getSource();
        
        if (component instanceof EditableValueHolder) {
            EditableValueHolder editableValueHolder = (EditableValueHolder) component;
            
            if (! editableValueHolder.isValid()) {
                component.getAttributes().put("styleClass", "invalidInput");
            } else {
                component.getAttributes().put("styleClass", "");
            }
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof UIComponent;
    }
}
