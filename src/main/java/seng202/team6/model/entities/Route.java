package seng202.team6.model.entities;

public class Route {

    String AirlineID;
    String Airline;
    String SourceAirport;
    int SourceAirportID;
    String DestinationAirport;
    int DestinationAirportID;
    char Codeshare;
    int Stops;
    String Equipment;

    public Route(String newAirlineID, String newAirline, String newSourceAirport, int newSourceAirportID,
                 String newDestinationAirport, int newDestinationAirportID, char newCodeshare, int newStops,
                 String newEquipment) {
        AirlineID = newAirlineID;
        Airline = newAirline;
        SourceAirport = newSourceAirport;
        SourceAirportID = newSourceAirportID;
        DestinationAirport = newDestinationAirport;
        DestinationAirportID = newDestinationAirportID;
        Codeshare = newCodeshare;
        Stops = newStops;
        Equipment = newEquipment;
    }

    public String GetAirlineID() {
        return AirlineID;
    }

    public void SetAirlineID(String airlineID) {
        AirlineID = airlineID;
    }

    public String GetAirline() {
        return Airline;
    }

    public void SetAirline(String airline) {
        Airline = airline;
    }

    public String GetSourceAirport() {
        return SourceAirport;
    }

    public void SetSourceAirport(String sourceAirport) {
        SourceAirport = sourceAirport;
    }

    public int GetSourceAirportID() {
        return SourceAirportID;
    }

    public void SetSourceAirportID(int sourceAirportID) {
        SourceAirportID = sourceAirportID;
    }

    public String GetDestinationAirport() {
        return DestinationAirport;
    }

    public void SetDestinationAirport(String destinationAirport) {
        DestinationAirport = destinationAirport;
    }

    public int GetDestinationAirportID() {
        return DestinationAirportID;
    }

    public void SetDestinationAirportID(int destinationAirportID) {
        DestinationAirportID = destinationAirportID;
    }

    public char GetCodeshare() {
        return Codeshare;
    }

    public void SetCodeshare(char codeshare) {
        Codeshare = codeshare;
    }

    public int GetStops() {
        return Stops;
    }

    public void SetStops(int stops) {
        Stops = stops;
    }

    public String GetEquipment() {
        return Equipment;
    }

    public void SetEquipment(String equipment) {
        Equipment = equipment;
    }
}
