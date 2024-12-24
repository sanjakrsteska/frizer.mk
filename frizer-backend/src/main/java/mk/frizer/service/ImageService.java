package mk.frizer.service;

import jakarta.transaction.Transactional;
import mk.frizer.domain.ImageEntity;
import mk.frizer.domain.Salon;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    Optional<Salon> saveImage(Long id, MultipartFile file, Integer imageNo) throws IOException;
    Optional<Salon> saveBackgroundImage(Long id, MultipartFile file) throws IOException;
    byte[] getImage(Long id, Long imageId);
}
