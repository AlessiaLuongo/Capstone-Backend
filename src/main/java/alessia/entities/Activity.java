package alessia.entities;

import alessia.entities.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activities")
@NamedQuery(name = "getTheBestActivities", query = "SELECT a FROM Activity a ORDER BY a.rate DESC")

public class Activity extends Post{
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public Activity(String title, String description, LocalDate creationDate, boolean outdoor, long numberOfVisits, int rate, double price, List<String> listOfPictures, User user, LocalDate startDate, LocalDate endDate, EventType eventType) {
        super(title, description, creationDate, outdoor, numberOfVisits, rate, price, listOfPictures, user);
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
    }
}
