package mk.frizer.service;


import mk.frizer.domain.BusinessOwner;
import mk.frizer.domain.Salon;

import java.util.List;
import java.util.Optional;

public interface BusinessOwnerService {
    List<BusinessOwner> getBusinessOwners();
    Optional<BusinessOwner> getBusinessOwnerById(Long id);
    Optional<BusinessOwner> getBusinessOwnerByBaseUserId(Long id);
    Optional<BusinessOwner> createBusinessOwner(Long baseUserId);
    Optional<BusinessOwner> deleteBusinessOwnerById(Long id);
    Optional<BusinessOwner> addSalonToBusinessOwner(Long businessOwnerId, Salon salon);
    Optional<BusinessOwner> editSalonForBusinessOwner(Salon salon);
}
