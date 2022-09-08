package tripDemo.model;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Passenger implements Comparable<Passenger>{
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public int compareTo(Passenger o) {
        return new CompareToBuilder()
                .append(getId(), o.getId())
                .append(getFirstName(), o.getFirstName())
                .append(getMiddleName(), o.getMiddleName())
                .append(getLastName(), o.getLastName())
                .toComparison();
    }

    public static class Builder {
        private final Passenger passenger;

        public Builder() {
            passenger = new Passenger();
        }

        public Builder withRandomCompletely() {
            passenger.firstName = RandomStringUtils.random(5, true, false);
            passenger.middleName = RandomStringUtils.random(5, true, false);
            passenger.lastName = RandomStringUtils.random(5, true, false);
            return this;
        }

        public Passenger build() {
            return passenger;
        }
    }
}
