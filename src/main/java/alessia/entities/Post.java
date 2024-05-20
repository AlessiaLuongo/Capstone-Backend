package alessia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Post {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    private boolean outdoor;
    @Column(name = "number_of_visits")
    private long numberOfVisits;
    private int rate;
    private double price;
    private String picture;





    public Post(String title, String description, LocalDate creationDate, boolean outdoor, long numberOfVisits, int rate, double price, String picture) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.outdoor = outdoor;
        this.numberOfVisits = numberOfVisits;
        this.rate = rate;
        this.price = price;
        this.picture = picture;

    }
}
