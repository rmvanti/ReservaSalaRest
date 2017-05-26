package reservasala.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import reservasala.control.dao.DaoImpl;
import reservasala.control.dao.IDao;
import reservasala.model.Place;

/**
 * @author rmvanti
 */
@Path("place")
public class PlaceService {

    private final EntityManager manager;
    private final IDao<Place,Integer> daoPlace;
    
    public PlaceService(){
        this.manager = Persistence.createEntityManagerFactory("ReservaSalaRESTPU").createEntityManager();
        this.daoPlace = new DaoImpl(Place.class, this.manager);
    }//fim class
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaces(){
        return Response.ok().entity(new GenericEntity<List<Place>>(this.daoPlace.findAll()){})
                .header("Access-Control-Allow-Origin", "*")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }        
    
}//fim clas
