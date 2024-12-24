package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.frizer.domain.ImageEntity;
import mk.frizer.domain.Salon;
import mk.frizer.domain.enums.ImagePosition;
import mk.frizer.repository.ImageRepository;
import mk.frizer.repository.SalonRepository;
import mk.frizer.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final SalonRepository salonRepository;

    @Transactional
    public Optional<Salon> saveImage(Long id, MultipartFile file, Integer imageNo) throws IOException {
        //TODO delete previous image on the same position
        Optional<Salon> salon = salonRepository.findById(id);
            if (salon.isPresent()) {
            byte[] imageBytes = file.getBytes();
            ImagePosition imagePosition = ImagePosition.fromInt(imageNo);
            ImageEntity imageEntity = new ImageEntity(imageBytes, salon.get().getId(), imagePosition);
            imageEntity = imageRepository.save(imageEntity);

            // TODO Create an event here for the next functionallity?
            salon.get().getImages().put(imageEntity.getId(), imagePosition);
            return Optional.of(salonRepository.save(salon.get()));
            
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Salon> saveBackgroundImage(Long id, MultipartFile file) throws IOException {
        Optional<Salon> salon = salonRepository.findById(id);
        if (salon.isPresent()) {
            Long oldImageId = salon.get().getBackgroundImage();
            byte[] imageBytes = file.getBytes();
            ImageEntity imageEntity = new ImageEntity(imageBytes, salon.get().getId(), true);
            imageEntity = imageRepository.save(imageEntity);

            salon.get().setBackgroundImage(imageEntity.getId());

            if (oldImageId != null) {
                imageRepository.deleteById(oldImageId);
            }
            return Optional.of(salonRepository.save(salon.get()));
        }
        return Optional.empty();
    }

    @Transactional
    public byte[] getImage(Long id, Long imageId) {
        Optional<Salon> optionalSalon = salonRepository.findById(id);
        if (optionalSalon.isPresent()) {
            Salon salon = optionalSalon.get();
            if (salon.getBackgroundImage() != null && salon.getBackgroundImage().equals(imageId)) {
                Optional<ImageEntity> imageEntity = imageRepository.findById(salon.getBackgroundImage());
                if (imageEntity.isPresent()) {
                    return imageEntity.get().getImage();
                }
            }
            Long newImageId = salon.getImages().keySet().stream().filter(i -> i.equals(imageId)).findFirst().orElse(null);
            if (newImageId != null) {
                Optional<ImageEntity> imageEntity = imageRepository.findById(newImageId);
                if (imageEntity.isPresent()) {
                    return imageEntity.get().getImage();
                }
            }
        }
        return null;
    }
}