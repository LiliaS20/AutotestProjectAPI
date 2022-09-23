package tripDemo.hibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table (name = "passenger")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PassengerEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private long id;

    @Column (name = "FIRST_NAME")
    private String firstName;

    @Column (name = "MIDDLE_NAME")
    private String middleName;

    @Column (name ="LAST_NAME")
    private String lastName;
}
