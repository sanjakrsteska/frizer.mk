package mk.frizer.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.SalonSimpleDTO;
import mk.frizer.domain.enums.ImagePosition;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    @ManyToOne
    private City city;

    private String phoneNumber;
    @OneToMany(mappedBy = "salon", fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "salon")
    private List<Treatment> salonTreatments;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Nullable
    private BusinessOwner owner;

    @Nullable
    private Long backgroundImage = null;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "salon_images", joinColumns = @JoinColumn(name = "salon_id"))
    @MapKeyColumn(name = "image_id")
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    Map<Long, ImagePosition> images = new HashMap<>();

    private Double rating;
    private Integer numberOfReviews;

    private Float latitude;
    private Float longitude;

    public Salon(String name, String description, String location, City city, String phoneNumber,
                 BusinessOwner owner, Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.employees = new ArrayList<>();
        this.salonTreatments = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.owner = owner;
        this.backgroundImage = null;
        this.images = new HashMap<>();
        this.rating = 0.0;
        this.numberOfReviews = 0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SalonSimpleDTO toDto(){
        return SalonSimpleDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .location(this.location)
                .city(this.city.getName())
                .phoneNumber(this.phoneNumber)
                .employeesIds(employees.stream().map(Employee::getId).toList())
                .salonTreatmentsIds(salonTreatments.stream().map(Treatment::getId).toList())
                .tagsIds(tags.stream().map(Tag::getId).toList())
                .ownerId(owner != null ? owner.getBaseUser().getId() : -1)
                .backgroundImage(this.backgroundImage)
                .images(this.images)
                .rating(this.rating)
                .numberOfReviews(this.numberOfReviews)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }

    public void addReview(Double grade){
        this.rating = (this.rating * this.numberOfReviews + grade) / (this.numberOfReviews + 1);
        this.numberOfReviews++;
    }

    public void deleteReview(Double grade){
        this.rating = (this.rating * this.numberOfReviews - grade) / (this.numberOfReviews - 1);
        this.numberOfReviews--;
    }

    public void updateReview(Double newGrade, Double oldGrade){
        this.rating = (this.rating * this.numberOfReviews - oldGrade + newGrade) / (this.numberOfReviews);
    }
}