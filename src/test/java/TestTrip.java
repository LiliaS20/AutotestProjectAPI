import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tripDemo.dictionaries.IPathEnum;
import tripDemo.dictionaries.TripPathEnum;
import tripDemo.helper.ApiHelper;
import tripDemo.model.ConfigQA;
import tripDemo.model.JsonGenerator;
import tripDemo.model.Passenger;
import tripDemo.model.Trip;

import java.util.ArrayList;
import java.util.Map;

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
    }
}
