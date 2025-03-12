package ku.cs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;

import ku.cs.controller.*;
import ku.cs.repository.*;
import ku.cs.service.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        String user;
        String password;
        String dataSourceUrl = "jdbc:mysql://localhost/sakbp";
        
        user = System.getenv("SPRING_DATASOURCE_USERNAME");
        password = System.getenv("SPRING_DATASOURCE_PASSWORD");
        
        // Connect to Database
        System.out.println("Connecting to " + dataSourceUrl);
        
        try {
            conn = DriverManager.getConnection(dataSourceUrl, user, password);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }


        int port = 25565;

        // create repository
        UserRepository userResponsitory = new UserRepository(conn);
        MusicianRoleRepository roleResponsitory = new MusicianRoleRepository(conn);
        StereoTypeRepository stereoTypeResponsitory = new StereoTypeRepository(conn);
        EventRepository eventRepository = new EventRepository(conn);
        RequirementRepository requirementRepository = new RequirementRepository(conn);
        StereoRepository stereoRepository = new StereoRepository(conn);
        MusicianRepository musicianRepository = new MusicianRepository(conn); // เพิ้ม MusicianRepository


        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HelloController());
        server.createContext("/reg", new SignUpController(new SignUpService(userResponsitory)));
        server.createContext("/login", new LoginController(new LoginService(userResponsitory)));
        server.createContext("/userinfo", new UserInfoController(new UserService(userResponsitory)));
        server.createContext("/updatepassword", new UpdatePasswordController(new UserService(userResponsitory)));
        server.createContext("/updateuserinfo", new UpdateUserInfoController(new UserService(userResponsitory)));
        server.createContext("/getallusers", new GetAllUserController(new UserService(userResponsitory)));
        server.createContext("/getmusicianroles", new GetRolesController(new MusicianRoleService(roleResponsitory)));
        server.createContext("/getstereotypes", new GetStereoTypeController(new StereoTypeService(stereoTypeResponsitory)));
        server.createContext("/create_event", new CreateEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/event_list", new EventListController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/create_stereo", new CreateStereoController(new StereoService(stereoRepository, userResponsitory)));
        server.createContext("/event", new GetEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/stereo", new GetStereoController(new StereoService(stereoRepository, userResponsitory)));
        server.createContext("/stereo_list", new StereoListController(new StereoService(stereoRepository, userResponsitory)));
        server.createContext("/set_available_roles", new SetAvailableRoleController(new UserService(userResponsitory)));
        server.createContext("/get_available_roles", new GetAvailableRoleController(new UserService(userResponsitory)));
        server.createContext("/request_musician", new RequestMusicianController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/request_stereo", new RequestStereoController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/get_requested_events", new GetRequestedEventsController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/accept_musician_event", new AcceptMusicianEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/reject_musician_event", new RejectMusicianEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/accept_stereo_event", new AcceptStereoEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/reject_stereo_event", new RejectStereoEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/approve", new ApproveEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/cancel", new CancelEventController(new EventService(eventRepository, requirementRepository, userResponsitory)));
        server.createContext("/rate_musician", new RateMusicianController(new MusicianService(musicianRepository))); // เพิ้ม
        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}