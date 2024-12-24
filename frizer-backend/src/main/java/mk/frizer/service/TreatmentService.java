package mk.frizer.service;

import jakarta.transaction.Transactional;
import mk.frizer.domain.Treatment;
import mk.frizer.domain.dto.TreatmentAddDTO;
import mk.frizer.domain.dto.TreatmentUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface TreatmentService{
    List<Treatment> getTreatments();
    List<Treatment> getTreatmentsForSalon(Long id);
    Optional<Treatment> getTreatmentById(Long id);
    Optional<Treatment> createTreatment(TreatmentAddDTO treatmentAddDTO);
    Optional<Treatment> updateTreatment(Long id, TreatmentUpdateDTO treatmentUpdateDTO);
    Optional<Treatment> deleteTreatmentById(Long id);

    @Transactional
    Optional<Treatment> deleteTreatmentByIdFromSalon(Long id, Long salonId);

    List<Treatment> getTreatmentsByIds(List<Long> ids);
}
