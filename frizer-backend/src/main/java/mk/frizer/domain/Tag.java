package mk.frizer.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.TagSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Salon> salonsWithTag;

    public Tag(String name) {
        this.name = name;
        salonsWithTag = new ArrayList<>();
    }

    public TagSimpleDTO toDto(){
        return TagSimpleDTO.builder()
                .id(this.id)
                .name(this.name)
                .salonsWithTagIds(salonsWithTag.stream().map(Salon::getId).toList())
                .build();
    }
}
