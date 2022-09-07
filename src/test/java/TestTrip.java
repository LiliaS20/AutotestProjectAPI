import io.restassured.specification.Argument;
import org.testng.annotations.Test;
import tripDemo.model.Passenger;
import tripDemo.model.Trip;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestTrip {

    @Test
    public void createTrip() {
        Trip trip = new Trip();

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

      /*  given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .body(trip)
                .when()
                .post("http://localhost:8080/trip/createTrip")
                .thenReturn();*/

        Trip tripResult = given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .body(trip)
                .when()
                .post("http://localhost:8080/trip/createTrip")
                .as(Trip.class);
    }

    @Test
    public void getTrip() {
        given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("http://localhost:8080/trip/getTrip/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("townFrom", equalTo("Moscow"));
    }

    @Test
    public void deleteTrip() {
        given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .delete("http://localhost:8080/trip/deleteTrip/4")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void putTrip() {
        Trip trip = new Trip();

        trip.setId(7L);
        trip.setCompanyId(2L);
        trip.setPlane("plane№2");
        trip.setTownFrom("Moscow");
        trip.setTownTo("St. Petersburg1");
        trip.setTimeOut("2021-05-16T03:31:43");
        trip.setTimeIn("2021-05-15T05:31:43");


        given()
                .log().all(true)
                .contentType("application/json")
                .accept("application/json")
                .body(trip)
                .when()
                .put("http://localhost:8080/trip/putTrip")
                .thenReturn();
    }
}
