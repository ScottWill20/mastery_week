package learn.reserving.models;

import java.math.BigDecimal;

public class Host {
    private String hostId;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private int zipCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public Host() {}

    public Host(String id, String lastName, String email, String phone, String address, String city, String state, int zipCode, BigDecimal standardRate, BigDecimal weekendRate) {
        this.hostId = id;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }

    public String getHostId() {
        return hostId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }
}
