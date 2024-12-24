package mk.frizer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.ReviewJoinDTO;
import mk.frizer.domain.dto.simple.ReviewSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BaseUser author;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;
    private Double rating;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm")
    private LocalDateTime date;

    public Review(BaseUser author, Employee employee, Double rating, String comment) {
        this.author = author;
        this.employee = employee;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public ReviewSimpleDTO toDto(){
        return ReviewSimpleDTO.builder()
                .id(this.id)
                .authorId(author.getId())
                .employeeId(employee.getId())
                .rating(this.rating)
                .comment(this.comment)
                .date(this.date)
                .build();
    }
    public ReviewJoinDTO toJoinedDto(){
        return ReviewJoinDTO.builder()
                .id(this.id)
                .authorId(author.getId())
                .employeeId(employee.getId())
                .rating(this.rating)
                .comment(this.comment)
                .date(this.date)
                .authorFirstName(author.getFirstName())
                .authorLastName(author.getLastName())
                .employeeFullName(employee.getFullName())
                .build();
    }
}
