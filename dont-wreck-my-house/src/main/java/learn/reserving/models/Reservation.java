package learn.reserving.models;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reservation {
    private int resId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Host host;
    private Guest guest;
    private BigDecimal total;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal setTotal() {

        for (LocalDate date = checkIn; date.isBefore(checkOut); date = date.plusDays(1)) {

            if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                total = total.add(getHost().getWeekendRate());
            } else {
                total = total.add(getHost().getStandardRate());
            }
        }
        return total;
    }

}