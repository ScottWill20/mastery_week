package learn.reserving.ui;

import learn.reserving.data.DataException;
import learn.reserving.models.Guest;
import learn.reserving.models.Host;
import learn.reserving.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }
        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));

    }

    public String getHostEmail() {
        return io.readRequiredString("Host Email: ");
    }

    public String getGuestEmail() {
        return io.readRequiredString("Guest Email: ");
    }

    public Reservation chooseReservation(List<Reservation> reservations) {

        displayReservations(reservations);

        if (reservations.size() == 0) {
            return null;
        }
        int reservationId = io.readInt("Reservation ID: ");
        Reservation reservation = reservations.stream().filter(i -> i.getResId() == reservationId)
                .findFirst().orElse(null);

        if (reservation == null) {
            displayStatus(false, String.format("No reservation with ID %s found.",reservationId));
        }

        return reservation;
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found with that host.");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n",
                    reservation.getResId(),
                    reservation.getCheckIn(),
                    reservation.getCheckOut(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getEmail());
        }
    }

    public Reservation makeReservation(Guest guest, Host host) {
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setCheckIn(io.readLocalDate("Check-In (MM/dd/yyyy): "));
        reservation.setCheckOut(io.readLocalDate("Check-Out (MM/dd/yyyy): "));
        reservation.setTotal();
        return reservation;
    }

    public void editReservation(Reservation reservation) {
        displayHeader("Editing Reservation " + reservation.getResId());

        reservation.setCheckIn(io.readLocalDate(String.format("Check In (%s/%s/%s): ",
                reservation.getCheckIn().getMonth().getValue(),
                reservation.getCheckIn().getDayOfMonth(),
                reservation.getCheckIn().getYear())));

        reservation.setCheckOut(io.readLocalDate(String.format("Check Out (%s/%s/%s): ",
                reservation.getCheckOut().getMonth().getValue(),
                reservation.getCheckOut().getDayOfMonth(),
                reservation.getCheckOut().getYear())));

        reservation.setTotal();
    }


    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displaySummary(Reservation reservation) {
        displayHeader("Summary");
        io.println("Check In: " + reservation.getCheckIn());
        io.println("Check Out: " + reservation.getCheckOut());
        io.println("Total: " + reservation.getTotal());
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
    public void displayException(DataException ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());

    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = io.readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            io.println("[INVALID] Please enter 'y' or 'n'.");
        }
    }
}
