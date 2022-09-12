# Project Plan

Start with Models - Location and Host are one and the same
 - Reservation model should work similar to Forage model, pulls data from both Guest and Host

Next is Data Layer - Class and Interface for each model
 - DataException class to handle throwables

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
private BigDecimal costPerNight 

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
private double stayPeriod

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

## Data Tests
### GuestFileRepositoryTest
SEED_FILE_PATH
TEST_FILE_PATH
private final GuestFileRepository repository

@BeforeEach
- setup()
    - Paths seedPath
    - Paths testPath
    - Files.copy()

@Test shouldFindAll
@Test shouldFindByEmail


### HostFileRepositoryTest
SEED_FILE_PATH
TEST_FILE_PATH

@BeforeEach
- setup()
    - Paths seedPath
    - Paths testPath
    - Files.copy() 
  
@Test shouldFindAll
@Test shouldFindByEmail

### ReservationFileRepositoryTest
SEED_FILE_PATH
TEST_FILE_PATH

@BeforeEach
- setup()
    - Paths seedPath
    - Paths testPath
    - Files.copy()

@Test shouldFindById
@Test shouldAdd
@Test shouldUpdate
@Test shouldNotUpdateUnknownId
@Test shouldDelete
@Test shouldNotDeleteUnknownId

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

publicList<Reservation> findByEmail



## Tasks

## Schedule

## Approach

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
