package reservasala.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import reservasala.control.dao.DaoImpl;
import reservasala.control.dao.IDao;
import reservasala.model.MeetingRoom;

/**
 * @author rmvanti
 */
@Path("meetingRoom")
public class MeetingRoomService {

    private final EntityManager manager;
    private final IDao<MeetingRoom, Integer> daoRoom;
    
    public MeetingRoomService(){
        this.manager = Persistence.createEntityManagerFactory("ReservaSalaRESTPU").createEntityManager();
        this.daoRoom = new DaoImpl(MeetingRoomService.class, this.manager);
    }//fim construtor
    
    @GET
    @Path("{placeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMeettingRooms(@PathParam("placeId") int placeId){
        List<MeetingRoom> list = null;
        return Response.ok().entity(new GenericEntity<List<MeetingRoom>>(list){})
                .header("Access-Control-Allow-Origin", "*")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }
    
}//fim class
