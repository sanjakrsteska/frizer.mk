package mk.frizer.listeners;

import mk.frizer.domain.Treatment;
import mk.frizer.domain.events.TreatmentCreatedEvent;
import mk.frizer.domain.events.TreatmentUpdatedEvent;
import mk.frizer.service.SalonService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TreatmentEventHandler {
    private final SalonService salonService;

    public TreatmentEventHandler(SalonService salonService) {
        this.salonService = salonService;
    }

    @EventListener
    public void onTreatmentCreated(TreatmentCreatedEvent event) {
        Treatment treatment = (Treatment) event.getSource();
        salonService.addTreatmentToSalon(treatment);
    }

    @EventListener
    public void onTreatmentUpdated(TreatmentUpdatedEvent event) {
        Treatment treatment = (Treatment)event.getSource();
        salonService.editTreatmentForSalon(treatment);
    }
}
