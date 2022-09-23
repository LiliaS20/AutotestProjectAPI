package tripDemo.hibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "trip")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TripEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private CompanyEntity company;

    @Column (name = "plane")
    private String plane;

    @Column (name = "TOWN_FROM")
    private String townFrom;

    @Column (name = "TOWN_TO")
    private String townTo;

    @Column(name = "time_out")
    private LocalDateTime timeOut;

    @Column(name = "time_in")
    private LocalDateTime timeIn;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable (name = "TRIP_PASSENGER",
            joinColumns = {@JoinColumn (name = "trip_id")},
            inverseJoinColumns = {@JoinColumn (name = "passenger_id")}
    )
    private List<PassengerEntity> passengerList;
}
