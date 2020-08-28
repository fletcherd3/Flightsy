package seng202.team6.model.events;

/**
 * This class is for flight plans
 */
public class Flight extends Event {
    String Airline;
    String OriginAirport;
    String DestinationAirport;
    String OriginCity;
    String OriginCountry;
    String DestinatioCity;
    String DestinationCountry;

    /**
     *
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param OCity Origin City
     * @param OCountry Origin Country
     * @param DCity Destination City
     * @param DCountry Destination Country
     * @param newAirline Flight's airline
     * @param OAirport Origin Airport
     * @param DAirport Destination Airport
     */
    Flight(int D, int M, int Y, String T, String N, String OCity,
           String OCountry, String DCity, String DCountry, String newAirline, String OAirport, String DAirport) {
        super(D, M, Y, T, N);
        Airline = newAirline;
        OriginAirport = OAirport;
        DestinationAirport = DAirport;
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinatioCity = DCity;
        DestinationCountry = DCountry;
    }
}
