package sit.int221.oasip.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private Integer id;

    @NotNull(message = "Name is not null")
    @Size(min = 1 , max = 100 , message = "Name is not empty and must between 0 - 100")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Email(message = "Error invalid email address")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Roles role;

//    @Column(name = "role", nullable = false)
//    private String role;

    @NotNull(message = "Password is not null")
//    @Size(min = 7, max = 14, message = "size must be between 7 and 14")
    @Column(name = "password", nullable = false , length = 100 )
    private String password;

    @CreationTimestamp
    @Column(name = "createdOn", nullable = false)
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name = "updatedOn", nullable = false)
    private Timestamp updatedOn;


@JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Event> events;

}