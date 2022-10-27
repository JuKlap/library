package management.system.library.business.repository.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "authors")
public class AuthorDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "authors"
    )
    private Set<BookDAO> books = new HashSet<>();

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surname")
    private String surname;


}
