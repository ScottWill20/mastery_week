package learn.reserving.ui;

import learn.reserving.data.DataException;
import learn.reserving.domain.GuestService;
import learn.reserving.domain.HostService;
import learn.reserving.domain.ReservationService;
import learn.reserving.domain.Result;
import learn.reserving.models.Guest;
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
    public Controller(ReservationService reservationService, HostService hostService, GuestService guestService, View view, ConsoleIO io) {
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House!");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");

    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewByHost();
                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }



    private void viewByHost() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        String email = view.getHostEmail();
        List<Reservation> reservations = reservationService.findReservationsByHost(email);
//        reservations.stream().sorted()
        if (reservations == null || reservations.isEmpty()) {
            view.displayStatus(false,"That host is not in the system.");
            return;
        }
        view.displayReservations(reservations);
        view.enterToContinue();
    }

    private void addReservation() throws DataException {
        view.displayHeader(MainMenuOption.ADD_RESERVATION.getMessage());
        Guest guest = getGuest();
        if (guest == null) {
            System.out.println("No Guest with that email found.");
            return;
        }
        Host host = getHost();
        if (host == null) {
            System.out.println("No Host with that email found.");
            return;
        }
        Reservation reservation = view.makeReservation(guest,host);

        view.displaySummary(reservation);
        if (!view.readBoolean("Is this okay? [y/n]: ")) {
            return;
        }

        Result<Reservation> result = reservationService.add(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false,result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s created.",result.getPayload().getResId());
            view.displayStatus(true,successMessage);
        }
        view.enterToContinue();

    }

    private void updateReservation() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        Guest guest = getGuest();
        if (guest == null) {
            view.displayStatus(false,"No Guest with that email found.");
            return;
        }
        Host host = getHost();
        if (host == null) {
            view.displayStatus(false,"No Host with that email found.");
            return;
        }

        // print header last name and location of host
        view.displayHeader(host.getLastName() + " - " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationService.findResById
                (host.getEmail(), guest.getGuestId());

        if (reservations.isEmpty()) {
            view.displayStatus(false,"No reservations found.");
            return;
        }
        Reservation reservation = view.chooseReservation(reservations);

        view.editReservation(reservation);

        view.displaySummary(reservation);
        if (!view.readBoolean("Is this okay? [y/n]: ")) {
            return;
        }

        Result<Reservation> result = reservationService.update(reservation);

        if (!result.isSuccess()) {
            view.displayStatus(false,result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s updated.",reservation.getResId());
            view.displayStatus(true,successMessage);
        }
        view.enterToContinue();

    }

    private void cancelReservation() throws DataException {
        view.displayHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        Guest guest = getGuest();
        if (guest == null) {
            System.out.println("No Guest with that email found.");
            return;
        }
        Host host = getHost();
        if (host == null) {
            System.out.println("No Host with that email found.");
            return;
        }

        List<Reservation> reservations = reservationService.findResById
                (host.getEmail(), guest.getGuestId());

        if (reservations == null || reservations.isEmpty()) {
            view.displayStatus(false,"No reservations found.");
            return;
        }
        view.displayHeader(host.getLastName() + " - " + host.getCity() + ", " + host.getState());

        Reservation reservation = view.chooseReservation(reservations);
        Result<Reservation> result = reservationService.delete(reservation);

        if (!result.isSuccess()) {
            view.displayStatus(false,result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s cancelled.",reservation.getResId());
            view.displayStatus(true,successMessage);
        }
        view.enterToContinue();
    }


    private Guest getGuest() {
        String guestEmail = view.getGuestEmail();
        return guestService.findByEmail(guestEmail);
    }

    private Host getHost() {
        String hostEmail = view.getHostEmail();
        return hostService.findByEmail(hostEmail);

    }

}
