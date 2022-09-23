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
    run();
    }

    private static void run() {
        ApplicationContext container = new AnnotationConfigApplicationContext(App.class);
        Controller controller = container.getBean(Controller.class);
        controller.run();
    }
}
