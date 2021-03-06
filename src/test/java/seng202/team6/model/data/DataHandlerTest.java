package seng202.team6.model.data;

import org.junit.*;
import org.junit.Assert.*;
import seng202.team6.model.entities.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.junit.Assert.*;

public class DataHandlerTest {
    private Random random;
    private int randomBound = 10000000;
    private DataHandler dataHandler;
    private ArrayList<Filter> filters;

    private Airline testAirline1;
    private Airline testAirline2;
    private Airline testAirline3;
    private Airline testAirline4;
    private Airline testAirline5;
    private Airline testEmptyAirline;
    private ArrayList<Airline> testAirlines;
    private ArrayList<Airline> actualAirlines;

    private Airport testAirport1;
    private Airport testAirport2;
    private Airport testAirport3;
    private Airport testAirport4;
    private Airport testAirport5;
    private Airport testEmptyAirport;
    private ArrayList<Airport> testAirports;
    private ArrayList<Airport> actualAirports;

    private Route testRoute1;
    private Route testRoute2;
    private Route testRoute3;
    private Route testRoute4;
    private Route testRoute5;
    private Route testEmptyRoute;
    private ArrayList<Route> testRoutes;
    private ArrayList<Route> actualRoutes;

    public void fullClear() {
        filters.clear();
        testAirlines.clear();
        actualAirlines.clear();
        testAirports.clear();
        actualAirports.clear();
        testRoutes.clear();
        actualRoutes.clear();

    }

    @Before
    public void InitializeTest() {
        random = new Random();
        dataHandler = new DataHandler();
        filters = new ArrayList<Filter>();

        testAirline1 = new Airline(random.nextInt(randomBound), "Virgin Airlines", "Virgin", "VI",
                "VIR", "VIRGIN", "Australia", 'Y');
        testAirline2 = new Airline(random.nextInt(randomBound), "Singapore Airlines", "Singapore", "SN",
                "SNG", "SINGAPORE", "Signapore", 'Y');
        testAirline3 = new Airline(random.nextInt(randomBound), "Qatar Airways", "Qatar", "QA",
                "QAT", "QATAR", "Qatar", 'Y');
        testAirline4 = new Airline(random.nextInt(randomBound), "Emirates", "Emirates", "EM",
                "EMI", "EMIRATES", "United Arab Emirates", 'Y');
        testAirline5 = new Airline(random.nextInt(randomBound), "Lufthansa", "Luft", "LF",
                "LFT", "LUFTHANSA", "Germany", 'Y');
        testEmptyAirline = new Airline(random.nextInt(randomBound), null, null, null, null,
                null, null, null);
        testAirlines = new ArrayList<Airline>();
        actualAirlines = new ArrayList<Airline>();

        testAirport1 = new Airport(random.nextInt(randomBound), "London Heathrow Airport", "London", "England",
                "LHR", "LOND", (float)51.470020, (float)-0.454295, 25, 1, 'U');
        testAirport2 = new Airport(random.nextInt(randomBound), "Los Angeles Airport", "Los Angeles", "United States of America",
                "LAX", "LOSX", (float)33.94279, (float)-118.410042, 38, -7, 'U');
        testAirport3 = new Airport(random.nextInt(randomBound), "Tokyo Haneda Airport", "Tokyo", "Japan",
                "HND", "HNDA", (float)35.5494, (float)139.7798, 11, 9, 'A');
        testAirport4 = new Airport(random.nextInt(randomBound), "Amsterdam Airport Schipol", "Amsterdam", "Netherlands",
                "AMS", "AMSD", (float)52.3105, (float)4.7683, -3, 1, 'A');
        testAirport5 = new Airport(random.nextInt(randomBound), "Hong Kong Airport", "Hong Kong", "Hong Kong",
                "HKG", "HGKG", (float)22.3080, (float)113.9185, 9, 8, 'A');
        testEmptyAirport = new Airport(random.nextInt(randomBound), null, null, null, null,
                null, null, null, null, null, null);
        testAirports = new ArrayList<Airport>();
        actualAirports = new ArrayList<Airport>();

        testRoute1 = new Route(testAirline1.getAirlineID(), testAirline1.getName(), testAirport1.getName(), testAirport1.getAirportID(),
                testAirport2.getName(), testAirport2.getAirportID(), ' ', 0, "CR2");
        testRoute2 = new Route(testAirline2.getAirlineID(), testAirline2.getName(), testAirport3.getName(), testAirport3.getAirportID(),
                testAirport4.getName(), testAirport4.getAirportID(), ' ', 0, "CR2");
        testRoute3 = new Route(testAirline3.getAirlineID(), testAirline3.getName(), testAirport5.getName(), testAirport5.getAirportID(),
                testAirport1.getName(), testAirport1.getAirportID(), ' ', 0, "A81");
        testRoute4 = new Route(testAirline4.getAirlineID(), testAirline4.getName(), testAirport2.getName(), testAirport2.getAirportID(),
                testAirport3.getName(), testAirport4.getAirportID(), ' ', 0, "AN4");
        testRoute5 = new Route(testAirline5.getAirlineID(), testAirline5.getName(), testAirport4.getName(), testAirport4.getAirportID(),
                testAirport5.getName(), testAirport5.getAirportID(), ' ', 0, "142");
        testEmptyRoute = new Route(random.nextInt(randomBound), null, null, random.nextInt(randomBound), null,
                random.nextInt(randomBound), null, null, null);
        testRoutes = new ArrayList<Route>();
        actualRoutes = new ArrayList<Route>();
    }

    /**
     * Test inserting one airline into the database
     */
    @Test
    public void testInsertOneAirline() {
        testAirlines.add(testAirline1);
        try {
            dataHandler.InsertAirlines(testAirlines);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
            filters.add(filter);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(testAirlines.get(0), actualAirlines.get(0));
        fullClear();
    }

    /**
     * Test inserting two airlines into the database
     */
    @Test
    public void testInsertTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {
            dataHandler.InsertAirlines(testAirlines);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
            filters.add(filter2);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting five airlines into the database
     */
    @Test
    public void testInsertFiveAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);
        try {
            dataHandler.InsertAirlines(testAirlines);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("id_airline = %d", testAirline5.getAirlineID()), "");
            filters.add(filter5);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting an empty airline into the database
     * !!! Leave ignored until the database schema is updated to prevent empty parameters from being entered
     */
    @Test @Ignore
    public void testInsertEmptyAirline() {
        testAirlines.add(testEmptyAirline);
        try {
            dataHandler.InsertAirlines(testAirlines);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test inserting one airport into the database
     */
    @Test
    public void testInsertOneAirport() {
        testAirports.add(testAirport1);
        try {
            dataHandler.InsertAirports(testAirports);
            Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
            filters.add(filter);
            actualAirports = dataHandler.FetchAirports(filters);
            Assert.assertEquals(testAirports.get(0), actualAirports.get(0));
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        fullClear();
    }

    /**
     * Test inserting two airports into the database
     */
    @Test
    public void testInsertTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            dataHandler.InsertAirports(testAirports);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "");
            filters.add(filter2);
            actualAirports = dataHandler.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting five airports in the database
     */
    @Ignore @Test
    public void testInsertFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            System.out.println("What");
            dataHandler.InsertAirports(testAirports);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("id_airport = %d", testAirport3.getAirportID()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("id_airport = %d", testAirport4.getAirportID()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("id_airport = %d", testAirport5.getAirportID()), "");
            filters.add(filter5);
            System.out.println("What");
            actualAirports = dataHandler.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting an empty airport (should error) in the database
     * !!! Leave ignored until the database schema is updated to prevent empty parameters from being entered
     */
    @Ignore @Test
    public void testInsertEmptyAirport() {
        testAirports.add(testEmptyAirport);
        try {
            dataHandler.InsertAirports(testAirports);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test inserting one route into the database
     */
    @Test
    public void testInsertOneRoute() {
        testRoutes.add(testRoute1);
        try {
            dataHandler.InsertRoutes(testRoutes);
            Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id " +
                            " = %d", testRoute1.getAirlineID(), testRoute1.getSourceAirportID(),
                    testRoute1.getDestinationAirportID()), "");
            filters.add(filter);
            actualRoutes = dataHandler.FetchRoutes(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(testRoutes.get(0), actualRoutes.get(0));
        fullClear();
    }

    /**
     * Test inserting two routes into the database
     */
    @Ignore @Test
    public void testInsertTwoRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        try {
            dataHandler.InsertRoutes(testRoutes);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute1.getAirlineID(), testRoute1.getSourceAirportID(),
                    testRoute1.getDestinationAirportID()), "");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute2.getAirlineID(), testRoute2.getSourceAirportID(),
                    testRoute2.getDestinationAirportID()), "");
            filters.add(filter2);
            actualRoutes = dataHandler.FetchRoutes(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testRoutes);
        Collections.sort(actualRoutes);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testRoutes.get(i), actualRoutes.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting five routes into the database
     */
    @Ignore @Test
    public void testInsertFiveRoutes() {
        // insert five routes
    }

    /**
     * Test inserting an empty route (should error) into the database
     * !!! Leave ignored until the database schema is updated to prevent empty parameters from being entered
     */
    @Ignore @Test
    public void testInsertEmptyRoute() {
        testRoutes.add(testEmptyRoute);
        try {
            dataHandler.InsertRoutes(testRoutes);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test updating one airline within the database
     */
    @Test
    public void testUpdateOneAirline() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), "VirginBlue", null, null, null, null,
                    null, null);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
            filters.add(filter);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetName("VirginBlue");
        assertEquals(testAirline1, actualAirlines.get(0));
        fullClear();
    }

    /**
     * Test updating two airlines within the database
     */
    @Test
    public void testUpdateTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), "VirginGreen", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataHandler.updateAirline(testAirline2.getAirlineID(), "VirginPurple", null, null, null, null,
                    null, null);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
            filters.add(filter2);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetName("VirginGreen");
        testAirlines.get(1).SetName("VirginPurple");
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 2; i++) {
            assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        fullClear();
    }

    /**
     * Test updating five airlines within the database
     */
    @Ignore @Test
    public void testUpdateFiveAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), "VirginAlpha", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataHandler.updateAirline(testAirline2.getAirlineID(), "VirginBeta", null, null, null, null,
                    null, null);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
            filters.add(filter2);
            dataHandler.updateAirline(testAirline3.getAirlineID(), "VirginCharlie", null, null, null, null,
                    null, null);
            Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "");
            filters.add(filter2);
            dataHandler.updateAirline(testAirline4.getAirlineID(), "VirginDelta", null, null, null, null,
                    null, null);
            Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "");
            filters.add(filter2);
            dataHandler.updateAirline(testAirline5.getAirlineID(), "VirginEcho", null, null, null, null,
                    null, null);
            Filter filter5 = new Filter(format("id_airline = %d", testAirline5.getAirlineID()), "");
            filters.add(filter2);
            actualAirlines = dataHandler.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetName("VirginAlpha");
        testAirlines.get(1).SetName("VirginBeta");
        testAirlines.get(2).SetName("VirginCharlie");
        testAirlines.get(3).SetName("VirginDelta");
        testAirlines.get(4).SetName("VirginEcho");
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 5; i++) {
            assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        fullClear();
    }

    /**
     * Test updating an airline with empty parameters (should throw exception) within the database
     */
    @Test
    public void testUpdateAirlineEmpty() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), null, null, null, null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with one character within the database
     */
    @Test
    public void testUpdateAirlineOneCharIATA() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), null, null, "W", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with three characters within the database
     */
    @Test
    public void testUpdateAirlineThreeCharIATA() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), null, null, "WAP", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with two characters within the database
     */
    @Test
    public void testUpdateAirlineTwoCharICAO() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), null, null, null, "WA", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with four characters within the database
     */
    @Test
    public void testupdateAirlineFourCharICAO() {
        testAirlines.add(testAirline1);
        try {dataHandler.InsertAirlines(testAirlines);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirline(testAirline1.getAirlineID(), null, null, null, "WAPP", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating one airport within the database
     */
    @Ignore @Test
    public void testUpdateOneAirport() {
        testAirports.add(testAirport1);
        try {dataHandler.InsertAirports(testAirports);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataHandler.updateAirport(testAirport1.getAirportID(), "Alpha", null, null, null, null,
                    null, null, null, null, null);
            Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
            filters.add(filter);
            actualAirports = dataHandler.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetName("Alpha");
        assertEquals(testAirport1, actualAirports.get(0));
        fullClear();
    }

    /**
     * Test updating two airports within the database
     */
    @Ignore @Test
    public void testUpdateTwoAirports() {
        // update two airlines

    }

    /**
     * Test updating five airports within the database
     */
    @Ignore @Test
    public void testUpdateFiveAirports() {
        // update five airlines
    }

    /**
     * Test updating an airport with empty parameters within the database
     */
    @Ignore @Test
    public void testUpdateAirportEmpty() {
        // update an airline with empty parameters
    }

    /**
     * Test updating invalid IATA with two characters within the database
     */
    @Ignore @Test
    public void testUpdateAirportTwoCharIATA() {
        // update airline with invalid IATA with one char
    }

    /**
     * Test updating invalid IATA with four characters within the database
     */
    @Ignore @Test
    public void testUpdateAirportFourCharIATA() {
        // update airline with invalid IATA with three chars
    }

    /**
     * Test updating invalid ICAO with three characters within the database
     */
    @Ignore @Test
    public void testUpdateAirportThreeCharICAO() {
        // update airline with invalid ICAO with two chars
    }

    /**
     * Test updating invalid ICAO with five characters within the database
     */
    @Ignore @Test
    public void testupdateAirportFiveCharICAO() {
        // update airline with invalid ICAO with four chars
    }

    /**
     * Test updating an airport with invalid parameters within the database
     */
    @Ignore @Test
    public void testInvalidAirportParams() {
        // update airline with invalid parameter data
    }

    /**
     * Test updating a route within the database
     */
    @Ignore @Test
    public void testUpdateOneRoute() {
        // update one route
    }

    /**
     * Test updating two routes within the database
     */
    @Ignore @Test
    public void testUpdateTwoRoutes() {
        // update two routes
    }

    /**
     * Test updating five routes within the database
     */
    @Ignore @Test
    public void testUpdateFiveRoutes() {
        // update five routes
    }

    /**
     * Test updating a route with empty parameters (should throw exception) within the database
     */
    @Ignore @Test
    public void testUpdateRouteEmpty() {
        // update a route with empty parameters
    }

    /**
     * Test updating a route with invalid ICAO with three characters within the database
     */
    @Ignore @Test
    public void testUpdateSourceThreeCharICAO() {
        // update route with invalid source airport IATA with three chars
    }

    /**
     * Test updating a route with invalid ICAO with five characters within the database
     */
    @Ignore @Test
    public void testUpdateSourceFiveCharICAO() {
        // update route with invalid source airport IATA with five chars
    }

    /**
     * Test updating a route with invalid ICAO with three characters within the database
     */
    @Ignore @Test
    public void testUpdateDestinationThreeCharICAO() {
        // update route with invalid destination airport IATA with three chars
    }

    /**
     * Test updating a route with invalid ICAO with five characters within the database
     */
    @Ignore @Test
    public void testUpdateDestinationFiveCharICAO() {
        // update route with invalid destination airport IATA with five chars
    }

    /**
     * Test updating a route with empty parameters (should throw exception) within the database
     */
    @Ignore @Test
    public void testInvalidRouteParams() {
        // update route with invalid parameter data
    }

    /**
     * Test deleting an airline in the database
     */
    @Ignore @Test
    public void testDeleteOneAirline() {
        // delete one airline
    }

    /**
     * Test deleting two airlines in the database
     */
    @Ignore @Test
    public void testDeleteTwoAirlines() {
        // delete two airlines
    }

    /**
     * Test deleting five airlines in the database
     */
    @Ignore @Test
    public void testDeleteFiveAirlines() {
        // delete five airlines
    }

    /**
     * Test deleting an airline without providing an ID in the database
     */
    @Ignore @Test
    public void testDeleteAirlineEmptyID() {
        // delete airline where a null AirlineID is provided
    }

    /**
     * Test deleting an airline with an invalid ID in the database
     */
    @Ignore @Test
    public void testDeleteAirlineInvalidID() {
        // delete airline where an invalid AirlineID is provided
    }

    /**
     * Test deleting an airport in the database
     */
    @Ignore @Test
    public void testDeleteOneAirport() {
        // delete one airline
    }

    /**
     * Test deleting two airports in the database
     */
    @Ignore @Test
    public void testDeleteTwoAirports() {
        // delete two airlines
    }

    /**
     * Test deleting five airports in the database
     */
    @Ignore @Test
    public void testDeleteFiveAirports() {
        // delete five airlines
    }

    /**
     * Test deleting an airport without providing an ID (should thrown exception) in the database
     */
    @Ignore @Test
    public void testDeleteAirportEmptyID() {
        // delete airline where a null AirlineID is provided
    }

    /**
     * Test deleting an airport with an invalid ID in the database
     */
    @Ignore @Test
    public void testDeleteAirportInvalidID() {
        // delete airline where an invalid AirlineID is provided
    }

    /**
     * Test deleting one route in the database
     */
    @Ignore @Test
    public void testDeleteOneRoute() {
        // delete one route
    }

    /**
     * Test deleting two routes in the database
     */
    @Ignore @Test
    public void testDeleteTwoRoutes() {
        // delete two routes
    }

    /**
     * Test deleting five routes in the database
     */
    @Ignore @Test
    public void testDeleteFiveRoutes() {
        // delete five routes
    }

    /**
     * Test deleting a route with an empty ID (should throw exception) in the database
     */
    @Ignore @Test
    public void testDeleteRouteEmptyID() {
        // delete route where a null AirlineID is provided
    }

    /**
     * Test deleting a route with an invalid ID in the database
     */
    @Ignore @Test
    public void testDeleteRouteInvalidAirlineID() {
        // delete airline where an invalid AirlineID is provided
    }

    /**
     * Test deleting a route with an invalid source airport ID in the database
     */
    @Ignore @Test
    public void testDeleteRouteInvalidSourceID() {
        // delete airline where an invalid SourceAirportID is provided
    }

    /**
     * Test deleting a route with an invalid destination airport ID in the database
     */
    @Ignore @Test
    public void testDeleteRouteInvalidDestinationID() {
        // delete airline where an invalid DestinationAirportID is provided
    }

}
