import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.*;
import tripDemo.comparator.TripDBComparator;
import tripDemo.hibernate.TripEntity;
import tripDemo.model.PassengerRepository;
import tripDemo.model.TripRepository;
import tripDemo.model.TripSteps;
import tripDemo.comparator.TripComparator;
import tripDemo.dictionaries.IPathEnum;
import tripDemo.dictionaries.TripPathEnum;
import tripDemo.helper.ApiHelper;
import tripDemo.model.ConfigQA;
import tripDemo.model.JsonGenerator;
import tripDemo.model.Passenger;
import tripDemo.model.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class TestTrip extends BaseTest{
    private Trip trip;
    private TripEntity tripEntity;

    @BeforeMethod
    public void init() {
        trip = new Trip.Builder()
                .withRandomMainInfo(1)
                .withPassengers(new ArrayList<Passenger>() {{
                    for (int i = 0; i < RandomUtils.nextInt(1, 3); i++) {
                        add(new Passenger.Builder().withRandomCompletely().build());
                    }
                }}).build();
    }

    @BeforeMethod (groups = {"withExistTrip"})
    public void  prepareTrip() {
        trip = TripSteps.createTrip(trip);
    }

    @AfterMethod (groups = {"withAddedEntity"})
    public void deleteEntity() {
        TripRepository.getInstance().delete(tripEntity);
    }

    @DataProvider
    public Object[][] prepareTrips() {
        return new Object[][]{
                {"1", new Trip.Builder()
                        .withRandomMainInfo(1)
                        .withPassengers(new ArrayList<Passenger>() {{
                            for (int i = 0; i < RandomUtils.nextInt(1, 3); i++) {
                                add(new Passenger.Builder().withRandomCompletely().build());
                            }
                        }}).build()},
                {"2", new Trip.Builder().withRandomMainInfo(1)
                        .withPlane("test plane")
                        .withPassengers(new ArrayList<Passenger>() {{
                            for (int i = 0; i < RandomUtils.nextInt(1, 3); i++) {
                                add(new Passenger.Builder().withRandomCompletely().build());
                            }
                        }}).build()},
        };
    }

    @Test (groups = {"withAddedEntity"}, dataProvider = "prepareTrips")
    public void createTrip(String testNumber, Trip trip) {
        System.out.println("test number = " + testNumber);
        Trip responseTrip = TripSteps.sendPost(trip);
        new TripComparator(responseTrip, trip).compare();
        tripEntity = TripRepository.getInstance().getById(TripEntity.class, responseTrip.getId());
        new TripDBComparator(responseTrip, tripEntity).compare();
        System.out.println(tripEntity);
     /*   TripRepository tripRepository = new TripRepository();
        PassengerRepository passengerRepository = new PassengerRepository();
        Trip tripFromBD = tripRepository.getById(responseTrip.getId());
        for (Passenger passenger : responseTrip.getPassengerList()) {
            Passenger passengerFormBD = passengerRepository.getById(passenger.getId());
            tripFromBD.getPassengerList().add(passengerFormBD);
        }
        Collections.sort(tripFromBD.getPassengerList());
        new TripComparator(createTrip, tripFromBD).compare();*/
    }

    @Test (groups = {"withExistTrip", "withAddedEntity"})
    public void getTrip() {
        String path = serviceDataMap.get(TripPathEnum.GET_TRIP);
        Response response = ApiHelper.get(path, 10);
        System.out.println(response.getBody().prettyPrint());
    }

    @Test (groups = {"withExistTrip", "withAddedEntity"})
    public void putTrip() {
        trip.setPlane("newPlane");
        Trip responseTrip = TripSteps.sendPut(trip);
        new TripComparator(trip, responseTrip).compare();
        tripEntity = TripRepository.getInstance().getById(TripEntity.class, responseTrip.getId());
        new TripDBComparator(responseTrip, tripEntity).compare();
       /* String body = JsonGenerator.toJsonString(putTrip);
        String path = serviceDataMap.get(TripPathEnum.PUT_TRIP);
        Response response = ApiHelper.put(path, body);
        System.out.println(response.getBody().prettyPrint());*/
    }

    @Test (groups = {"withExistTrip"})
    public void deleteTrip() {
        Trip responseTrip = TripSteps.sendDelete(trip.getId());
        new TripComparator(trip, responseTrip).compare();
        TripEntity tripEntity = TripRepository.getInstance().getById(TripEntity.class, responseTrip.getId());
        Assertions.assertThat(tripEntity).isNull();

        /*String path = serviceDataMap.get(TripPathEnum.DELETE_TRIP);
        Response response = ApiHelper.delete(path, 10);
        System.out.println(response.getBody().prettyPrint());*/
    }
}
