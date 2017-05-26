package reservasala.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import reservasala.control.dao.IIdentifiable;

/**
 * @author rmvanti
 */
@Entity
@XmlRootElement
public class Reserve implements Serializable, IIdentifiable<Integer> {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @OneToOne
    private MeetingRoom meetingRoom;
    
    private String requester;
    private boolean withCoffeeBreak;
    private int numberOfPeople;
    private String description;
        
    public Reserve(){}//fim construtor

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public boolean isWithCoffeeBreak() {
        return withCoffeeBreak;
    }

    public void setWithCoffeeBreak(boolean withCoffeeBreak) {
        this.withCoffeeBreak = withCoffeeBreak;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }        

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String message = "Id: %d, Solicitante: %s, %s, Inicia: %s, Encerra: %s, Coffe: %s, Pessoas: %d, Descrição: %s";
        return String.format(message,
                             getId(),
                             getRequester(),
                             getMeetingRoom().toString(),
                             sdf.format(getStartDate()),
                             sdf.format(getEndDate()),
                             isWithCoffeeBreak() ? "Sim" : "Não",
                             getNumberOfPeople(),
                             getDescription()
        );
    }
                                              
}//fim class
