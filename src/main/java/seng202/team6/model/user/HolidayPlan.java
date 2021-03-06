package seng202.team6.model.user;

import java.lang.Object;

import seng202.team6.model.data.DataHandler;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;

import java.util.ArrayList;

public class HolidayPlan {
    String Name; //No more than 25 chars
    private ArrayList<General> Itineraries= new ArrayList<>();
    private ArrayList<Flight> flights = new ArrayList<>(); //Should not exceed 30
    private static HolidayPlan Instance;

    /**
     * Singleton method for the holiday
     * @return Single HolidayPlan object
     */
    public static HolidayPlan GetInstance() {
        if (Instance == null) {
            Instance = new HolidayPlan();
        }
        return Instance;
    }

    /**
     * This method should be called when the user is adding a new flight to their HolidayPlan.
     * It should also check if it is below 30 before appending the flight to the array, and it should
     * check that the flight that is about to be appended is not in the array.
     * @param flight The flight that is to be added to the array Flights
     */
    public void FlightAppend(Flight flight) {
        if (flights.size() < 30) {
            flights.add(flight);
            System.out.println(String.format("%s, %s to %s, %s", flight.getOAirport(), flight.getOCity(), flight.getDAirport(), flight.getDCity()));
        }
    }

    /**
     * This method should be called when the user wants to append itineraries to their holiday plan
     * @param General The itinerary that is to be added to the array Itineraries
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param nCity The city where the general event is taking place
     * @param nCountry The country where the general event is taking place
     */
    public void ItineraryAppend(General General, int D, int M, int Y, String T, String N, String nCity, String nCountry) {
        General = new General(D, M, Y, T, N, nCity, nCountry);
        Itineraries.add(General);
    }

}
