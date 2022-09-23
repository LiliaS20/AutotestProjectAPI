package tripDemo.hibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "CLIENT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Setter
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column
    private LocalDate brithDay;
}
