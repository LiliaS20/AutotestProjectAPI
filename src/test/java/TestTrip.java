import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tripDemo.helper.ApiHelper;
import tripDemo.model.ConfigQA;
import tripDemo.model.JsonGenerator;
import tripDemo.model.Passenger;
import tripDemo.model.Trip;
import tripDemo.dictionaries.IPathEnum;
import tripDemo.dictionaries.TripPathEnum;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestTrip {
    private Map<IPathEnum, String> serviceDataMap;
    private Trip createTrip, putTrip;

    @BeforeClass
    public void init() {
        serviceDataMap = ConfigQA.getInstance().getServiceDataMap();

        createTrip = new Trip.Builder()
                .withRandomMainInfo(1)
                .withPassengers(new ArrayList<Passenger>() {{
                    for (int i = 0; i < RandomUtils.nextInt(1, 3); i++) {
                        add(new Passenger.Builder().withRandomCompletely().build());
                    }
                }}).build();

        putTrip = new Trip.Builder()
                .withRandomMainInfo(1)
                .withId(8L)
                .withPassengers(new ArrayList<Passenger>() {{
                    for (int i = 0; i < RandomUtils.nextInt(1, 3); i++) {
                        add(new Passenger.Builder().withRandomCompletely().build());
                    }
                }}).build();
    }

    @Test
    public void createTrip() {
        String body = JsonGenerator.toJsonString(createTrip);
        String path = serviceDataMap.get(TripPathEnum.CREATE_TRIP);
        Response response = ApiHelper.post(path, body);
        System.out.println(response.getBody().prettyPrint());

     /*   Trip trip = new Trip();
        trip.setCompanyId(2L);
        trip.setPlane("plane№2");
        trip.setTownFrom("Moscow");
        trip.setTownTo("St. Petersburg");
        trip.setTimeOut("2021-05-16T03:31:43");
        trip.setTimeIn("2021-05-15T05:31:43");

        List<Passenger> passengerList = new ArrayList<>();
        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("Andrey");
        passenger1.setMiddleName("Maksimovich");
        passenger1.setLastName("Ivanov");

        Passenger passenger2 = new Passenger();
        passenger2.setFirstName("Anna");
        passenger2.setMiddleName("Viktorovna");
        passenger2.setLastName("Petrova");

        passengerList.add(passenger1);
        passengerList.add(passenger2);

        trip.setPassengerList(passengerList);

          /   /   /   /   /   /   /   /   /   /   /   /   /   /   /   /

          given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .body(trip)
                .when()
                .post("http://localhost:8080/trip/createTrip")
                .thenReturn();

          /   /   /   /   /   /   /   /   /   /   /   /   /   /   /   /

                  Trip tripResult = given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .body(trip)
                .when()
                .post("http://localhost:8080/trip/createTrip")
                .as(Trip.class);*/
    }

    @Test
    public void getTrip() {
        String path = serviceDataMap.get(TripPathEnum.GET_TRIP);
        Response response = ApiHelper.get(path, 10);
        System.out.println(response.getBody().prettyPrint());
    }

    @Test
    public void deleteTrip() {
        String path = serviceDataMap.get(TripPathEnum.DELETE_TRIP);
        Response response = ApiHelper.delete(path, 10);
        System.out.println(response.getBody().prettyPrint());
    }

    @Test
    public void putTrip() {
        String body = JsonGenerator.toJsonString(putTrip);
        String path = serviceDataMap.get(TripPathEnum.PUT_TRIP);
        Response response = ApiHelper.put(path, body);
        System.out.println(response.getBody().prettyPrint());

        /*Trip trip = new Trip();
        trip.setId(7L);
        trip.setCompanyId(2L);
        trip.setPlane("plane№2");
        trip.setTownFrom("Moscow");
        trip.setTownTo("St. Petersburg1");
        trip.setTimeOut("2021-05-16T03:31:43");
        trip.setTimeIn("2021-05-15T05:31:43");*/
    }
}
