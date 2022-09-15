package learn.reserving.ui;

import learn.reserving.data.DataException;
import learn.reserving.domain.GuestService;
import learn.reserving.domain.HostService;
import learn.reserving.domain.ReservationService;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private final ReservationService reservationService;
    private final HostService hostService;
    private final GuestService guestService;
    private final View view;

    @Autowired
    public Controller(ReservationService reservationService, HostService hostService, GuestService guestService, View view) {
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House!");
        runAppLoop();
        view.displayHeader("Goodbye.");

    }

    private void runAppLoop() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewByHost();
                    break;
                case ADD_RESERVATION:
                    addReservation();
            }
        } while (option != MainMenuOption.EXIT);
    }



    private void viewByHost() {
        String email = view.getHostEmail();
        List<Reservation> reservations = reservationService.findReservationsByHost(email);
        view.displayReservations(reservations);
        view.enterToContinue();
    }

    private void addReservation() {
    }

}
