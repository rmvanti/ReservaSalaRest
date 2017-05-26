package reservasala.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import reservasala.control.dao.IIdentifiable;

/**
 * @author rmvanti
 */
@XmlRootElement
@Entity
public class MeetingRoom implements Serializable, IIdentifiable<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Place place;
    
    private String name;
    private int capacity;
    private boolean multimediaResources;
    private String description;
    
    public MeetingRoom(){}//fim construtor

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isMultimediaResources() {
        return multimediaResources;
    }

    public void setMultimediaResources(boolean multimediaResources) {
        this.multimediaResources = multimediaResources;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
        
    @Override
    public String toString() {        
        String message = "Local: %s, Sala: %s; Capacidade: %d, Multimídia: %s";
        return String.format(message,getPlace().getName(), getName(), getCapacity(), isMultimediaResources() ? "Sim" : "Não");
    }
            
}//fim class
