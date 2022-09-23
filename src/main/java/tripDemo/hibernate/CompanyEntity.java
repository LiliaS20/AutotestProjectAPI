package tripDemo.hibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "company")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompanyEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private long id;

    @Column (name = "NAME")
    private String nameCompany;

    @Column (name = "OPEN_DATE")
    private LocalDate openDate;
}
