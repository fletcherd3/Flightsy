package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.gui.controller.routefinder.AirportResultController;
import seng202.team6.gui.controller.routefinder.AnalysisDistanceController;
import seng202.team6.gui.controller.routefinder.FlightResultController;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FindRoutesController implements Initializable
{
    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";

    private final String subFolder = "routefinder";

    @FXML
    private VBox resultsPane;

    //Airport filtering
    @FXML
    private VBox airportFilterBox;
    private ArrayList<FilterTextField> airportFilterTextFields;
    private final String airportResultComponent = "airportresult";

    //Flight filtering
    @FXML
    private VBox flightFilterOriginBox;
    @FXML
    private VBox flightFilterDestinationBox;
    @FXML
    private Slider maxStopsSlider;
    private ArrayList<FilterTextField> flightFilterOriginTextFields;
    private ArrayList<FilterTextField> flightFilterDestinationTextFields;
    private final String flightResultComponent = "flightresult";

    //Route filtering
    @FXML
    private VBox routeFilterOriginBox;
    @FXML
    private VBox routeFilterDestinationBox;
    private ArrayList<FilterTextField> routeFilterOriginTextFields;
    private ArrayList<FilterTextField> routeFilterDestinationTextFields;

    //Data analysis
    @FXML
    private FilterTextField distanceOriginAirportIATAField;
    @FXML
    private FilterTextField distanceDestAirportIATAField;
    private final String analysisDistanceResultComponent = "analysisdistance";

    private MapController controller;

    /**
     * Initialize trip window component
     * @param url Url
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapController(webEngine);
        ResultController.SetMap(controller);

        //Grab all text filter components
        airportFilterTextFields = NodeHelper.GetAllNodes(airportFilterBox);
        flightFilterOriginTextFields = NodeHelper.GetAllNodes(flightFilterOriginBox);
        flightFilterDestinationTextFields = NodeHelper.GetAllNodes(flightFilterDestinationBox);
        routeFilterOriginTextFields = NodeHelper.GetAllNodes(routeFilterOriginBox);
        routeFilterDestinationTextFields = NodeHelper.GetAllNodes(routeFilterDestinationBox);
    }

    /**
     * Draw airport marks based on filters
     */
    @FXML
    private void OnAirportFilterButtonClicked() {
        OnResult();
        ArrayList<Filter> filters = FilterTextField.ExtractFilters(this.airportFilterTextFields);
        ArrayList<Airport> airports = DataHandler.GetInstance().FetchAirports(filters);

        if (airports == null) {
            return;
        }

        try {
            for (Airport airport : airports) {
                Pair<BorderPane, AirportResultController> pair = NodeHelper.LoadNode(subFolder, airportResultComponent);

                resultsPane.getChildren().add(pair.getKey());
                AirportResultController resultController = pair.getValue();
                resultController.SetAirport(airport);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        controller.ClearAll();
        controller.DrawAirportMarks(airports);
    }

    /**
     * Get all routes between source and destination airports
     */
    @FXML
    private void OnFlightFilterButtonClicked() {
        OnResult();

        Pair<ArrayList<Airport>, ArrayList<Airport>> airports = GetSourceAndDestinations(flightFilterOriginTextFields, flightFilterDestinationTextFields);
        ArrayList<Route> routes = DataHandler.GetInstance().FetchRoutes(airports.getKey(), airports.getValue(), (int)maxStopsSlider.getValue());

        try {
            for (Route route : routes) {
                Pair<BorderPane, FlightResultController> pair = NodeHelper.LoadNode(subFolder, flightResultComponent);
                resultsPane.getChildren().add(pair.getKey());
                FlightResultController resultController = pair.getValue();
                resultController.SetFlight(route);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get all route paths between source and destination airports
     */
    @FXML
    private void OnRouteFilterButtonClicked() {
        OnResult();

        Pair<ArrayList<Airport>, ArrayList<Airport>> airports = GetSourceAndDestinations(routeFilterOriginTextFields, routeFilterDestinationTextFields);
        ArrayList<RoutePath> paths = DataHandler.GetInstance().FetchRoutePaths(airports.getKey(), airports.getValue());

        /* TODO
        try {
            for (RoutePath path : paths) {

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */
    }

    /**
     * Display analysis information requested.
     */
    public void OnAnalyseButtonClicked()
    {
        ArrayList<FilterTextField> originFilters = new ArrayList<>();
        originFilters.add(distanceOriginAirportIATAField);
        ArrayList<FilterTextField> destFilters = new ArrayList<>();
        destFilters.add(distanceDestAirportIATAField);
        Pair<ArrayList<Airport>, ArrayList<Airport>> airportsList = GetSourceAndDestinations(originFilters, destFilters);

        try {
            if (airportsList.getKey() == null || airportsList.getValue() == null) {
                return;
            }

            Airport sourceAirport = airportsList.getKey().get(0);
            Airport destAirport = airportsList.getValue().get(0);

            double distance = sourceAirport.GetDistance(destAirport);

            OnResult();

            Pair<BorderPane, AnalysisDistanceController> pair = NodeHelper.LoadNode(subFolder, analysisDistanceResultComponent);
            resultsPane.getChildren().add(pair.getKey());
            AnalysisDistanceController resultController = pair.getValue();
            String title = String.format("%s to %s", sourceAirport.getIATA(), destAirport.getIATA());
            String destText = String.format("%.2f km", distance);
            //Display Distance
            resultController.SetInfo(title, destText);

            //Draw Airports and line between Airports
            ArrayList<Airport> airports = new ArrayList<>(Arrays.asList(sourceAirport, destAirport));
            controller.DrawAirportMarks(airports);
            controller.DrawLineBetween(airports);

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No airports found with desired IATA", ButtonType.OK);
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Called when results are to be shown
     */
    private void OnResult() {
        resultsPane.getChildren().clear();
        controller.ClearAll();
    }

    /**
     * Extract airports from filters
     * @param sourceFilters Source airport filters
     * @param destinationFilters Destination airport filters
     * @return Pair of source, destination airports
     */
    private Pair<ArrayList<Airport>, ArrayList<Airport>> GetSourceAndDestinations(ArrayList<FilterTextField> sourceFilters, ArrayList<FilterTextField> destinationFilters) {
        ArrayList<Filter> origin = FilterTextField.ExtractFilters(sourceFilters);
        ArrayList<Filter> destination = FilterTextField.ExtractFilters(destinationFilters);

        ArrayList<Airport> sourceAirports = DataHandler.GetInstance().FetchAirports(origin);
        ArrayList<Airport> destinationAirports = DataHandler.GetInstance().FetchAirports(destination);

        return new Pair<>(sourceAirports, destinationAirports);
    }


}
