package se.ningor.movies.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.model.SelectItem;

public class ListingLoader implements SystemEventListener {
    public static final String PROFESSIONS_KEY = "professions";

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        Map<String, Object> applicationMap = FacesContext.getCurrentInstance().
                                             getExternalContext().getApplicationMap();
        
        if (event instanceof PostConstructApplicationEvent) {
            
            //Load the listing data in the startup ...
            System.out.println("PostConstructApplicationEvent is called ...");
            
            applicationMap.put(PROFESSIONS_KEY, getSampleProfessionList());
        } else if (event instanceof PreDestroyApplicationEvent) {
            
            //Unload the listing data in the shutdown ...
            System.out.println("PreDestroyApplicationEvent is called ...");
            
            applicationMap.remove(PROFESSIONS_KEY);
        }        
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }
    
    private List<SelectItem> getSampleProfessionList() {
        List<SelectItem> sampleProfessions = new ArrayList<SelectItem>();
        
        sampleProfessions.add(new SelectItem("Profession1"));
        sampleProfessions.add(new SelectItem("Profession2"));
        sampleProfessions.add(new SelectItem("Profession3"));
        sampleProfessions.add(new SelectItem("Other"));        
        
        return sampleProfessions;
    }
}