# Project Plan
## Architecture

```
src
├───main
│   ├───java
│   │   └───learn
│   │       └───reserving
│   │           │   App.java
│   │           │
│   │           ├───data
│   │           │       DataException.java
│   │           │       HostFileRepository.java
│   │           │       HostRepository.java
│   │           │       GuestFileRepository.java
│   │           │       GuestRepository.java
│   │           │       ReservationFileRepository.java
│   │           │       ReservationRepository.java
│   │           │
│   │           ├───domain
│   │           │       HostService.java
│   │           │       GuestService.java
│   │           │       ReservationService.java
│   │           │       Response.java
│   │           │       Result.java
│   │           │
│   │           ├───models
│   │           │       Host.java
│   │           │       Guest.java
│   │           │       Reservation.java
│   │           │
│   │           └───ui
│   │                   ConsoleIO.java
│   │                   Controller.java
│   │                   GenerateRequest.java
│   │                   MainMenuOption.java (?)
│   │                   View.java
│   │
│   └───resources
└───test
    └───java
        └───learn
            └───foraging
                ├───data
                │       HostFileRepositoryTest.java
                │       HostRepositoryDouble.java
                │       GuestFileRepositoryTest.java
                │       GuestRepositoryDouble.java
                │       ReservationFileRepositoryTest.java
                │       ReservationRepositoryDouble.java
                │
                └───domain
                        ReservationServiceTest.java
```

# Models
### Host
public Class Host {

private String email
private String firstName
private String LastName

private string streetAddress
private String city
private String state
private int zipCode
private BigDecimal costPerWeekDay
private BigDecimal costPerWeekendDay

public Host constructor
getters
### Guest
public Class Guest {

private String email
private String firstName
private String lastName


public Guest constructor
getters

### Reservation
public Class Reservation {

private int id
private LocalDate checkIn
private LocalDate checkOut
private Host host;
private Guest guest;

getters and setters

public BigDecimal getTotal()


## Annotations
create resources folder in src/main
 - create data.properties file, add filePaths as needed for repos

annotate as needed - 
 - Repository
   - @Repository, @Value
 - Service
   - @Service
 - UI
   - @Component
   - Controller constructor - @Autowired
 - App
   - @ComponentScan
   - @PropertySource("classpath:data.properties") - connects to resources folder
   - Within Main:
     - ApplicationContext container
     - Controller controller
     - controller.run

# Data Layer

### Data Exception
extends Exception {
 - overloaded public DataException methods

### HostFileRepository 
implements HostRepository {

private static final String HEADER
private final String filePath
- class method (public HostFileRepository)
- findAll
- findByEmail

- HostRepository - Interface

### GuestFileRepository
implements GuestRepository {

- might need delimiter values instantiated
private static final String HEADER
private final String filePath
- class method (public GuestFileRepository)
- findAll
- findByEmail

- GuestRepository - Interface

### ReservationFileRepository 
implements ReservationRepository {
private static final String HEADER
private final String directory
- class method (public GuestFileRepository)
- findById
- add
- update
- delete
- getFilePath
- writeAll
- serialize
- deserialize

- ReservationRepository - Interface
# Domain Layer
### HostService
private final HostRepository repository;

public HostService()

public List<Host> findByEmail()
private Result<Host> validate()
 - likely more than one validate method

### GuestService
private final GuestRepository repository;

public GuestService()

public List<Guest> findByEmail

private Result<Guest> validate
- likely more than one validate method

### ReservationService
private final HostRepository repository;
private final GuestRepository repository;
private final ReservationRepository repository;

public ReservationService()

public List<Reservation> findByEmail,
public Result<Reservation> add,
public Result<Reservation> update,
public Result<Reservation> delete

private Result<Host> validate()

### Response
boolean isSuccess,
List<String> getErrorMessage,
void addErrorMessage

### Result
private T payload

getter and setter

## UI Layer

### ConsoleIO

Scanner,
DateTimeFormatter

rework print methods: print, println, printf

readString,
readRequiredString,
readDouble,
readInt (Range),
readInt (readRequired),
read LocalDate,
readBigDecimal (Range),
readBigDecimal (Invalid)

might need readBoolean for y/n response

### Controller

reference all services
reference View

public Controller constructor

run(),
runMenu()
 - Exit
 - Read
   - viewReservationsByHost
 - Create
   - makeReservation
 - Update
   - editReservation
 - Delete
   - CancelReservation

// support methods
- getHost
- getGuest

### MainMenuOption?
 - consider making

### View
public int getMenuOption/MainMenuOption getMenuOption

getReservations

getDates
- Start Date
- End Date
  - see getGenerateRequest in foraging

getGuestEmail,
getHostEmail,
boolean getConfirm,
enterToContinue?,
displayHeader,
displayException,
displayHeader,
displayStatus,
displayStatus overloaded?,


## Requirements
### View Reservations for Host
- [ ] Display all reservations for a host.

- [ ] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- [ ] If the host is not found, display a message.
- [ ] If the host has no reservations, display a message.
- [ ] Show all reservations for that host.
- [ ] Show useful information for each reservation: the guest, dates, totals, etc.
- [ ] Sort reservations in a meaningful way.

### Make a Reservation
- [ ] Books accommodations for a guest at a host.

- [ ] The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
- [ ] The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- [ ] Show all future reservations for that host so the administrator can choose available dates.
- [ ] Enter a start and end date for the reservation.
- [ ] Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday (Sunday, Monday, Tuesday, Wednesday, or Thursday) or a weekend (Friday or Saturday). If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
- [ ] On confirmation, save the reservation.

#### Validation

- [ ] Guest, host, and start and end dates are required.
- [ ] The guest and host must already exist in the "database". Guests and hosts cannot be created.
- [ ] The start date must come before the end date.
- [ ] The reservation may never overlap existing reservation dates.
- [ ] The start date must be in the future.

### Edit a Reservation
- [ ] Edits an existing reservation.

- [ ] Find a reservation.
- [ ] Start and end date can be edited. No other data can be edited.
- [ ] Recalculate the total, display a summary, and ask the user to confirm.

#### Validation

- [ ] Guest, host, and start and end dates are required.
- [ ] The guest and host must already exist in the "database". Guests and hosts cannot be created.
- [ ] The start date must come before the end date.
- [ ] The reservation may never overlap existing reservation dates.

### Cancel a Reservation
- [ ] Cancel a future reservation.

- [ ] Find a reservation.
- [ ] Only future reservations are shown.
- [ ] On success, display a message.

#### Validation

- [ ] You cannot cancel a reservation that's in the past.


## Tasks & Schedule

### Monday
- [ ] Create directory structure - 15 min
- [ ] Create classes and interfaces - 30 min
- [ ] wiring with annotations - 30 min
  - [ ] create resources folder
  - [ ] create data.properties file, create filePaths
  - [ ] wire up all classes with filePaths
- [ ] Models
  - [ ] Host - 15 min
  - [ ] Guest - 15 min
  - [ ] Reservation - 45 min
- Total - 2.5 hours
### Tuesday
- [ ] Data Layer
  - [ ] HostFileRepo - 1 hr
  - [ ] GuestFileRepo - 1 hr
  - [ ] ReservationFileRepo - 2 hr
  - [ ] ensure all interfaces contain necessary methods - 30 min
  - [ ] Test Data Layer - at least 1 hr
- Total - 5.5 hours

### Wednesday
- [ ] Domain Layer
  - [ ] HostService - 1 hr
  - [ ] GuestService - 1 hr
  - [ ] ReservationService 1.5 hr
  - [ ] Response/Result - 30 min
  - [ ] Testing - 2 hr
- Total - 5 hours

### Thursday
- [ ] UI Layer
  - [ ] ConsoleIO - 1 hr
  - [ ] Controller - at least 3 hrs
    - [ ] view - 1 hr
    - [ ] update - 1 hr
    - [ ] delete - 30 min
    - [ ] create - 1 hr
    - [ ] helper - 2 hr
- Total - 6.5 hours
### Friday
  - [ ] anything not completed in UI Layer
  - [ ] View - at least 2 hr
    - [ ] getter methods - 1 hr
    - [ ] helper methods - 1 hr
    - [ ] displayMethod - 45 min
  - [ ] polish - 2 hr
  - Total ~ 5 hours

### Weekend
 - Use to catch up on anything incomplete
 - Polish testing
 - confirm no validations need reworking
 - etc.


# Sample UI

```
Main Menu
=========
0. Exit
1. View Reservations for Host
2. Make a Reservation
3. Edit a Reservation
4. Cancel a Reservation
Select [0-4]:
```

```
View Reservations for Host
==========================
Host Email: bcharon56@storify.com

Charon: Tampa, FL
=================
ID: 8, 08/12/2020 - 08/18/2020, Guest: Carncross, Tremain, Email: tcarncross2@japanpost.jp
ID: 2, 09/06/2020 - 09/07/2020, Guest: Rogliero, Kayley, Email: kroglieroo@goo.gl
ID: 12, 10/12/2020 - 10/15/2020, Guest: Dodimead, Natal, Email: ndodimead7@yellowbook.com
ID: 7, 10/19/2020 - 10/20/2020, Guest: Nozzolii, Rebecka, Email: rnozzoliid7@webmd.com
ID: 4, 11/27/2020 - 11/28/2020, Guest: Ferminger, Carl, Email: cfermingerfr@bbb.org
ID: 5, 01/14/2021 - 01/18/2021, Guest: Garlee, Raina, Email: rgarlee9j@indiatimes.com
ID: 10, 01/24/2021 - 01/29/2021, Guest: Gaish, Bonnibelle, Email: bgaish3q@mediafire.com
ID: 1, 02/11/2021 - 02/16/2021, Guest: Carefull, Kendall, Email: kcarefullge@cnbc.com
ID: 11, 03/23/2021 - 03/25/2021, Guest: Cadreman, Marjorie, Email: mcadremanmv@nps.gov
ID: 6, 03/26/2021 - 03/31/2021, Guest: Dodimead, Natal, Email: ndodimead7@yellowbook.com
ID: 9, 06/04/2021 - 06/08/2021, Guest: Rossin, Vince, Email: vrossin68@miibeian.gov.cn
ID: 3, 06/23/2021 - 06/28/2021, Guest: Fraczak, Raymond, Email: rfraczakb5@ifeng.com
```

```
Make a Reservation
==================
Guest Email: spawlowiczhj@opera.com
Host Email: bcharon56@storify.com

Charon: Tampa, FL
=================
ID: 8, 08/12/2020 - 08/18/2020, Guest: Carncross, Tremain, Email: tcarncross2@japanpost.jp
ID: 2, 09/06/2020 - 09/07/2020, Guest: Rogliero, Kayley, Email: kroglieroo@goo.gl
ID: 12, 10/12/2020 - 10/15/2020, Guest: Dodimead, Natal, Email: ndodimead7@yellowbook.com
ID: 7, 10/19/2020 - 10/20/2020, Guest: Nozzolii, Rebecka, Email: rnozzoliid7@webmd.com
ID: 4, 11/27/2020 - 11/28/2020, Guest: Ferminger, Carl, Email: cfermingerfr@bbb.org
ID: 5, 01/14/2021 - 01/18/2021, Guest: Garlee, Raina, Email: rgarlee9j@indiatimes.com
ID: 10, 01/24/2021 - 01/29/2021, Guest: Gaish, Bonnibelle, Email: bgaish3q@mediafire.com
ID: 1, 02/11/2021 - 02/16/2021, Guest: Carefull, Kendall, Email: kcarefullge@cnbc.com
ID: 11, 03/23/2021 - 03/25/2021, Guest: Cadreman, Marjorie, Email: mcadremanmv@nps.gov
ID: 6, 03/26/2021 - 03/31/2021, Guest: Dodimead, Natal, Email: ndodimead7@yellowbook.com
ID: 9, 06/04/2021 - 06/08/2021, Guest: Rossin, Vince, Email: vrossin68@miibeian.gov.cn
ID: 3, 06/23/2021 - 06/28/2021, Guest: Fraczak, Raymond, Email: rfraczakb5@ifeng.com
Start (MM/dd/yyyy): 07/31/2020
End (MM/dd/yyyy): 08/05/2020

Summary
=======
Start: 07/31/2020
End 08/05/2020
Total: $2090.00
Is this okay? [y/n]: y

Success
=======
Reservation 13 created.
```

```
Edit a Reservation
==================
Guest Email: spawlowiczhj@opera.com
Host Email: bcharon56@storify.com

Charon: Tampa, FL
=================
ID: 13, 07/31/2020 - 08/05/2020, Guest: Pawlowicz, Shelba, Email: spawlowiczhj@opera.com
Reservation ID: 13

Editing Reservation 13
======================
Start (07/31/2020):
End (08/05/2020): 08/04/2020

Summary
=======
Start: 07/31/2020
End 08/04/2020
Total: $1710.00
Is this okay? [y/n]: y

Success
=======
Reservation 13 updated.
```

```
Cancel a Reservation
====================
Guest Email: spawlowiczhj@opera.com
Host Email: bcharon56@storify.com

Charon: Tampa, FL
=================
ID: 13, 07/31/2020 - 08/04/2020, Guest: Pawlowicz, Shelba, Email: spawlowiczhj@opera.com
Reservation ID: 13

Success
=======
Reservation 13 cancelled.
```
