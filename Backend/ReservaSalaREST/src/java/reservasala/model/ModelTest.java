

package reservasala.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import reservasala.control.dao.DaoImpl;
import reservasala.control.dao.IDao;

/**
 * @author rmvanti
 */
public class ModelTest {
    
    public static void main(String[] args) throws ParseException {
        EntityManager em = Persistence.createEntityManagerFactory("ReservaSalaRESTPU").createEntityManager();
        IDao<Place,Integer> daoPlace = new DaoImpl(Place.class, em);
        IDao<MeetingRoom,Integer> daoRoom = new DaoImpl(MeetingRoom.class, em);
        IDao<Reserve,Integer> daoReserve = new DaoImpl(Reserve.class, em);        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        /* ****************************************************************** */
        Place pc = new Place();
        pc.setName("Banana Ltda.");        
        daoPlace.insert(pc);        
        
        pc = new Place();
        pc.setName("Melancia Ltda.");        
        daoPlace.insert(pc);
                
        /* ****************************************************************** */
        pc = daoPlace.findById(1);
        
        MeetingRoom mr = new MeetingRoom();
        mr.setCapacity(15);
        mr.setName("SR-07");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 7. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));        
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(25);
        mr.setName("AD-09");
        mr.setMultimediaResources(true);
        mr.setDescription("Auditório 9. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(8);
        mr.setName("SR-01");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 1. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(5);
        mr.setName("SR-03");
        mr.setMultimediaResources(false);
        mr.setDescription("Sala de reuniões número 3. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(20);
        mr.setName("SR-02");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 2. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(150);
        mr.setName("AD-10");
        mr.setMultimediaResources(true);
        mr.setDescription("Auditório 10. Possui recurso multimídia");
        mr.setPlace(daoPlace.findById(1));
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        /* ****************************************************************** */
        pc = daoPlace.findById(2);
        
        mr = new MeetingRoom();
        mr.setCapacity(43);
        mr.setName("TX-07");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 7. Possui recurso multimídia");
        mr.setPlace(pc);        
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        
        mr = new MeetingRoom();
        mr.setCapacity(35);
        mr.setName("TX-08");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 7. Possui recurso multimídia");
        mr.setPlace(pc);        
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        mr = new MeetingRoom();
        mr.setCapacity(80);
        mr.setName("RX-17");
        mr.setMultimediaResources(true);
        mr.setDescription("Sala de reuniões número 7. Possui recurso multimídia");
        mr.setPlace(pc);        
        daoRoom.insert(mr);
        pc.addRoom(mr);
        daoPlace.update(pc);
        
        
        /* ****************************************************************** */
        Reserve rs = new Reserve();
        rs.setRequester("Adam Sandler");
        rs.setStartDate(sdf.parse("15/05/2017 - 09:00"));
        rs.setEndDate(sdf.parse("15/05/2017 - 18:00"));
        rs.setMeetingRoom(daoRoom.findById(5));
        rs.setWithCoffeeBreak(true);
        rs.setNumberOfPeople(5);
        daoReserve.insert(rs);
        
        rs = new Reserve();
        rs.setRequester("Vanessa Hiffer");
        rs.setStartDate(sdf.parse("15/05/2017 - 08:00"));
        rs.setEndDate(sdf.parse("15/05/2017 - 12:00"));
        rs.setMeetingRoom(daoRoom.findById(1));
        rs.setWithCoffeeBreak(true);
        rs.setNumberOfPeople(10);
        daoReserve.insert(rs);
        
        rs = new Reserve();
        rs.setRequester("Joran Van Swiegan");
        rs.setStartDate(sdf.parse("15/05/2017 - 13:00"));
        rs.setEndDate(sdf.parse("15/05/2017 - 22:00"));
        rs.setMeetingRoom(daoRoom.findById(3));
        rs.setWithCoffeeBreak(false);
        rs.setNumberOfPeople(5);
        daoReserve.insert(rs);
        
        rs = new Reserve();
        rs.setRequester("Alehandro Gonzalez");
        rs.setStartDate(sdf.parse("15/05/2017 - 08:00"));
        rs.setEndDate(sdf.parse("15/05/2017 - 22:00"));
        rs.setMeetingRoom(daoRoom.findById(6));
        rs.setWithCoffeeBreak(true);
        rs.setNumberOfPeople(150);
        daoReserve.insert(rs);                
        
        /* ****************************************************************** */
        System.out.println("");
        List<Reserve> reservas = daoReserve.findAll();        
        for (Reserve reserva : reservas) {
            System.out.println(reserva.toString());
        }//fim for
        
        pc = daoPlace.findById(1);
        System.out.println("");
        System.out.println("Local: " + pc.getName() +", Salas: " +pc.getRooms().size()+";");
        
    }//fim main
    
}//fim class

