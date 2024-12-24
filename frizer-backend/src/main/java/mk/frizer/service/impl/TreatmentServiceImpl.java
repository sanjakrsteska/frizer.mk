package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.domain.Employee;
import mk.frizer.domain.Salon;
import mk.frizer.domain.Treatment;
import mk.frizer.domain.dto.TreatmentAddDTO;
import mk.frizer.domain.dto.TreatmentUpdateDTO;
import mk.frizer.domain.events.TreatmentCreatedEvent;
import mk.frizer.domain.events.TreatmentUpdatedEvent;
import mk.frizer.domain.exceptions.SalonNotFoundException;
import mk.frizer.domain.exceptions.TreatmentNotFoundException;
import mk.frizer.repository.SalonRepository;
import mk.frizer.repository.TreatmentRepository;
import mk.frizer.service.TreatmentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final SalonRepository salonRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TreatmentServiceImpl(TreatmentRepository treatmentRepository, SalonRepository salonRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.treatmentRepository = treatmentRepository;
        this.salonRepository = salonRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Treatment> getTreatments() {
        return treatmentRepository.findAll();
    }

    @Override
    public List<Treatment> getTreatmentsForSalon(Long id) {
        return treatmentRepository.findAll().stream()
                .filter(t->t.getSalon().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Treatment> getTreatmentById(Long id) {
        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(TreatmentNotFoundException::new);
        return Optional.of(treatment);
    }

    @Override
    @Transactional
    public Optional<Treatment> createTreatment(TreatmentAddDTO treatmentAddDTO) {
        Salon salon = salonRepository.findById(treatmentAddDTO.getSalonId())
                .orElseThrow(SalonNotFoundException::new);

        Treatment treatment = new Treatment(treatmentAddDTO.getName(), salon, treatmentAddDTO.getPrice(), treatmentAddDTO.getDuration());
        treatmentRepository.save(treatment);

        applicationEventPublisher.publishEvent(new TreatmentCreatedEvent(treatment));
        return Optional.of(treatment);
    }

    @Override
    @Transactional
    public Optional<Treatment> updateTreatment(Long id, TreatmentUpdateDTO treatmentUpdateDTO) {
        Treatment treatment = getTreatmentById(id).get();
        treatment.setName(treatmentUpdateDTO.getName());
        treatment.setPrice(treatmentUpdateDTO.getPrice());
        treatment.setDurationMultiplier(treatmentUpdateDTO.getDuration());
        treatmentRepository.save(treatment);

        applicationEventPublisher.publishEvent(new TreatmentUpdatedEvent(treatment));
        return Optional.of(treatment);
    }

    @Override
    @Transactional
    public Optional<Treatment> deleteTreatmentById(Long id) {
        Treatment treatment = getTreatmentById(id).get();
        treatmentRepository.deleteById(id);
        return Optional.of(treatment);
    }
    @Override
    @Transactional
    public Optional<Treatment> deleteTreatmentByIdFromSalon(Long id, Long salonId) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Optional<Treatment> treatment = salon.getSalonTreatments().stream()
                .filter(t -> t.getId().equals(id)).findFirst();
        if (treatment.isPresent()) {
            return deleteTreatmentById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Treatment> getTreatmentsByIds(List<Long> ids) {
        return treatmentRepository.findAllById(ids);
    }
}
