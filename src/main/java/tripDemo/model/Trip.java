package tripDemo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Trip {
    private Long id;
    private Long companyId;
    private String plane;
    private String townFrom;
    private String townTo;
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeOut;
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeIn;
    private List<Passenger> passengerList = new ArrayList<>();

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public static class Builder {
        private final Trip trip;

        public Builder() {
            trip = new Trip();
        }
        public Builder withPassengers(List<Passenger> passengerList) {
            trip.passengerList.addAll(passengerList);
            Collections.sort(trip.passengerList);
            return this;
        }

        public Builder withPlane(String plane) {
            trip.plane = plane;
            return this;
        }

        public Builder withTownTo(String townTo) {
            trip.townTo = townTo;
            return this;
        }

        public Builder withId(Long id) {
            trip.id = id;
            return this;
        }

        public Builder withRandomMainInfo(long companyId) {
           // trip.id = Long.valueOf(RandomStringUtils.random(1, false, true));
            trip.companyId = companyId;
            trip.plane = RandomStringUtils.random(5, true, false);
            trip.townFrom = RandomStringUtils.random(10, true, false);
            trip.townTo = RandomStringUtils.random(10, true, false);
            trip.timeOut = LocalDateTime.now().plusDays(5).truncatedTo(ChronoUnit.SECONDS);
            trip.timeIn = LocalDateTime.now().plusDays(5).plusHours(5).truncatedTo(ChronoUnit.SECONDS);
            return this;
        }

        public Trip build() {
            return trip;
        }
    }
}
