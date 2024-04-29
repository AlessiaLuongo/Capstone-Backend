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
}