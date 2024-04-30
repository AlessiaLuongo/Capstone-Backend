package alessia.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "list_of_pictures")
    private List<String> listOfPictures;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String description, LocalDate creationDate, boolean outdoor, long numberOfVisits, int rate, double price, List<String> listOfPictures, User user) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.outdoor = outdoor;
        this.numberOfVisits = numberOfVisits;
        this.rate = rate;
        this.price = price;
        this.listOfPictures = listOfPictures;
        this.user = user;
    }
}
