package ku.cs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpServer;

import ku.cs.controller.AcceptMusicianEventController;
import ku.cs.controller.AcceptStereoEventController;
import ku.cs.controller.ApproveEventController;
import ku.cs.controller.CancelEventController;
import ku.cs.controller.CreateEventController;
import ku.cs.controller.CreateStereoController;
import ku.cs.controller.EventListController;
import ku.cs.controller.GetAllUserController;
import ku.cs.controller.GetAvailableRoleController;
import ku.cs.controller.GetEventController;
import ku.cs.controller.GetRequestedEventsController;
import ku.cs.controller.GetRolesController;
import ku.cs.controller.GetStereoController;
import ku.cs.controller.GetStereoTypeController;
import ku.cs.controller.HelloController;
import ku.cs.controller.LoginController;
import ku.cs.controller.RejectMusicianEventController;
import ku.cs.controller.RejectStereoEventController;
import ku.cs.controller.RequestMusicianController;
import ku.cs.controller.RequestStereoController;
import ku.cs.controller.SetAvailableRoleController;
import ku.cs.controller.SignUpController;
import ku.cs.controller.StereoListController;
import ku.cs.controller.UpdatePasswordController;
import ku.cs.controller.UpdateUserInfoController;
import ku.cs.controller.UserInfoController;
import ku.cs.repository.EventRepository;
import ku.cs.repository.MusicianRoleRepository;
import ku.cs.repository.RequirementRepository;
import ku.cs.repository.StereoRepository;
import ku.cs.repository.StereoTypeRepository;
import ku.cs.repository.UserRepository;
import ku.cs.service.EventService;
import ku.cs.service.LoginService;
import ku.cs.service.MusicianRoleService;
import ku.cs.service.SignUpService;
import ku.cs.service.StereoService;
import ku.cs.service.StereoTypeService;
import ku.cs.service.UserService;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = null;
        String user;
        String password;
        String dataSourceUrl = "jdbc:mysql://localhost/sa";
        
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
        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}