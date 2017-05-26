package reservasala.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import reservasala.control.dao.DaoImpl;
import reservasala.control.dao.IDao;
import reservasala.model.MeetingRoom;
import reservasala.model.Place;
import reservasala.model.Reserve;

/**
 * @author rmvanti
 */
@Path("/reserve")
public class ReserveService {
    
    private final EntityManager manager;
    private final IDao<Reserve, Integer> daoReserve;
    private final IDao<MeetingRoom, Integer> daoRoom;
    private final IDao<Place, Integer> daoPlace;
        
    public ReserveService() {
        this.manager = Persistence.createEntityManagerFactory("ReservaSalaRESTPU").createEntityManager();
        this.daoReserve = new DaoImpl(Reserve.class, this.manager);
        this.daoRoom = new DaoImpl(MeetingRoom.class, this.manager);
        this.daoPlace = new DaoImpl(Place.class, this.manager);        
    }//fim construtor
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getReserve(@PathParam("id") int id) {
        Reserve r = this.daoReserve.findById(id);
        return Response.ok().entity(r)
                .header("Access-Control-Allow-Origin", "*")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }      
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getReserves() {            
        return Response.ok().entity(new GenericEntity<List<Reserve>>(this.daoReserve.findAll()){})
                .header("Access-Control-Allow-Origin", "*")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces()
    public Reserve updateReserve(@PathParam("id") int id, String json){
        Reserve r = this.daoReserve.findById(id);
        //TODO: update bean
        //this.daoReserve.update(t);
        return r;
    }
    
    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public Response deleteReserve(@PathParam("id") int id) {
        this.daoReserve.deleteById(id);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")                                
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }
    
    @DELETE    
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public Response deleteReserve(String param) {
        System.out.println(param);
        //this.daoReserve.deleteById(id);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")                                
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }
      
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})    
    public Response insertReserve(String value) {
        String v = value.replace("\"", "");
        v = v.replace("{","");
        v = v.replace("}","");
        String[] split = v.split(",");
    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Reserve r = new Reserve();                        
        for (int i = 0; i < split.length; i++) {
            switch (split[i].split(":")[0]) {
                case "id": break;
                case "placeId": break;
                case "meetingRoomId":
                    r.setMeetingRoom(this.daoRoom.findById(Integer.parseInt(split[i].split(":")[1])));
                    break;                
                case "requester":
                    r.setRequester(split[i].split(":")[1]);
                    break;
                case "startDate": 
                    try {
                        split[i] = split[i].replace("T"," ");
                        split[i] = split[i].replaceFirst(":", "T");
                        r.setStartDate(sdf.parse(split[i].split("T")[1]));
                    } catch (ParseException ex) {
                        Logger.getLogger(ReserveService.class.getName()).log(Level.SEVERE, null, ex);
                    }                
                    break;
                case "endDate":
                    try {
                        split[i] = split[i].replace("T"," ");
                        split[i] = split[i].replaceFirst(":", "T");
                        r.setEndDate(sdf.parse(split[i].split("T")[1]));
                    } catch (ParseException ex) {
                        Logger.getLogger(ReserveService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "coffee":
                    if(split[i].split(":")[1].equals("false")){
                        r.setWithCoffeeBreak(false);
                    }else{
                        r.setWithCoffeeBreak(true);
                    }
                    break;
                case "numberOfPeople":
                    String number;
                    if(split[i].split(":").length == 1){
                        number = "0";
                    }else{
                        number = split[i].split(":")[1];
                    }
                    r.setNumberOfPeople(Integer.parseInt(number));
                    break;
                default: break;
            }            
        }
        this.daoReserve.insert(r);
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }            
    
    @OPTIONS
    @Path("/{id}")
    public Response getOptions(){
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Origin", "localhost/MeetingRoomReserve")
                .build();
    }
    
}//fim class
