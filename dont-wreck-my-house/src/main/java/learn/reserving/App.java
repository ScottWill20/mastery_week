package learn.reserving;

import learn.reserving.data.GuestFileRepository;
import learn.reserving.data.HostFileRepository;
import learn.reserving.data.ReservationFileRepository;
import learn.reserving.domain.GuestService;
import learn.reserving.domain.HostService;
import learn.reserving.domain.ReservationService;
import learn.reserving.ui.ConsoleIO;
import learn.reserving.ui.Controller;
import learn.reserving.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {
    configureWithAnnotationsAndRun();
//        configureManuallyAndRun();
    }

    private static void configureWithAnnotationsAndRun() {
        ApplicationContext container = new AnnotationConfigApplicationContext(App.class);
        Controller controller = container.getBean(Controller.class);
        controller.run();
    }

    private static void configureManuallyAndRun() {
        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("./data/reservations");
        GuestFileRepository guestFileRepository = new GuestFileRepository("./data/guests.csv");
        HostFileRepository hostFileRepository = new HostFileRepository("./data/hosts.csv");
        ConsoleIO consoleIO = new ConsoleIO();
        View view = new View(consoleIO);
        ReservationService reservationService = new ReservationService(reservationFileRepository,hostFileRepository,guestFileRepository);
        GuestService guestService = new GuestService();
        HostService hostService = new HostService();
        Controller controller = new Controller(reservationService,hostService,guestService,view);
        controller.run();
    }
}
