package mk.frizer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import mk.frizer.domain.dto.simple.CitySimpleDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class City{
    @Id
    private String name;
    @OneToMany(mappedBy = "city")
    private List<Salon> salonsInCity;

    public City() {
    }

    public City(String name) {
        this.name = name;
        this.salonsInCity = new ArrayList<>();
    }

    @Override
    public String toString(){
        return name;
    }

    public CitySimpleDTO toDto(){
        return CitySimpleDTO.builder()
                .name(this.name)
                .salonsIdsInCity(salonsInCity.stream().map(Salon::getId).toList())
                .build();
    }
}

