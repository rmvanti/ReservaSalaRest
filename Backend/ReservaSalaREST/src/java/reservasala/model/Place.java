

package reservasala.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import reservasala.control.dao.IIdentifiable;

/**
 * @author rmvanti
 */
@Entity
@XmlRootElement
public class Place implements Serializable, IIdentifiable<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
    private final List<MeetingRoom> rooms;
    
    private String name;
    
    public Place(){
        this.rooms = new ArrayList();
    }//fim construtor
    
    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<MeetingRoom> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
            
    /* ********************************************************************** */
    public void addRoom(MeetingRoom room){
        room.setPlace(this);
        this.rooms.add(room);
    }
    
    public void removeRoom(MeetingRoom room){
        this.rooms.remove(room);
        room.setPlace(null);
    }
    
    public void removeRoom(int index){
        MeetingRoom room = this.rooms.remove(index);
        room.setPlace(null);
    }
    
}//fim class
