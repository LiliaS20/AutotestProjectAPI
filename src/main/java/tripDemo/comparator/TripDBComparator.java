package tripDemo.comparator;

import org.assertj.core.api.Assertions;
import tripDemo.TripMapper;
import tripDemo.hibernate.TripEntity;
import tripDemo.model.Trip;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class TripDBComparator implements IComparator {
    private final Trip expected, actual;
    private final TripEntity tripEntity;

    public TripDBComparator(Trip expected, TripEntity tripEntity) {
        this.expected = expected;
        this.tripEntity = tripEntity;
        actual = TripMapper.INSTANCE.toDto(tripEntity);
        Collections.sort(actual.getPassengerList());
    }

    @Override
    public void compare() {
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        checkSpecificFields();
    }

    private void checkSpecificFields() {
        Assertions.assertThat(tripEntity.getCompany()).isNotNull();
    }
}
