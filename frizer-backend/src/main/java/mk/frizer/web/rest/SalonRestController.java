package mk.frizer.web.rest;

import mk.frizer.domain.Salon;
import mk.frizer.domain.dto.SalonAddDTO;
import mk.frizer.domain.dto.SalonUpdateDTO;
import mk.frizer.domain.dto.TagAddDTO;
import mk.frizer.domain.dto.simple.SalonSimpleDTO;
import mk.frizer.domain.exceptions.SalonNotFoundException;
import mk.frizer.service.ImageService;
import mk.frizer.service.SalonService;
import mk.frizer.utilities.CurrentUserHelper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/salons", "/api/salon"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class SalonRestController {
    private final SalonService salonService;
    private final ImageService imageService;

    public SalonRestController(SalonService salonService, ImageService imageService) {
        this.salonService = salonService;
        this.imageService = imageService;
    }

    @GetMapping()
    public List<SalonSimpleDTO> getAllSalons(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) Float distance,
                                             @RequestParam(required = false) Float rating,
                                             @RequestParam(required = false) Double latitude,
                                             @RequestParam(required = false) Double longitude) {
        if (name != null || city != null || distance != null || rating != null || latitude != null || longitude != null) {
            return salonService.filterSalons(name, city, distance, rating, latitude, longitude).stream().map(Salon::toDto).toList();
        }
        return salonService.getSalons().stream().map(Salon::toDto).toList();
    }

    @GetMapping("/ids")
    public List<SalonSimpleDTO> getAllSalonsByIds(@RequestParam List<Long> ids) {
        return salonService.getSalonsByIds(ids).stream().map(Salon::toDto).toList();
    }

    @GetMapping("/get-all-owned")
    public List<SalonSimpleDTO> getAllOwnedSalons() {
        return salonService.getOwnedSalons().stream().map(Salon::toDto).toList();
    }

    @GetMapping("/favourites")
    public List<SalonSimpleDTO> getAllFavouriteSalons() {
        return salonService.getFavouriteSalons().stream().map(Salon::toDto).toList();
    }


    @GetMapping("/top")
    public List<SalonSimpleDTO> getTopNSalons(@RequestParam Integer count) {
        return salonService.getTopNSalons(count).stream().map(Salon::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonSimpleDTO> getSalon(@PathVariable Long id) {
        return this.salonService.getSalonById(id)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<SalonSimpleDTO> createSalon(@RequestBody SalonAddDTO salonAddDTO) {
//        if (salonAddDTO.getBusinessOwnerId() == null) {
//        }
        // businessOwnerId is baseUserId
        salonAddDTO.setBusinessOwnerId(CurrentUserHelper.getId());
        return this.salonService.createSalon(salonAddDTO)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add-tag")
    public ResponseEntity<SalonSimpleDTO> addTagToSalon(@RequestBody TagAddDTO tagAddDTO) {
        return this.salonService.addTagToSalon(tagAddDTO)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add-favourites/{id}")
    public ResponseEntity<SalonSimpleDTO> addSalonToFavourites(@PathVariable Long id) {
        return this.salonService.addSalonToFavourites(id)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete-favourites/{id}")
    public ResponseEntity<SalonSimpleDTO> removeSalonFromFavourites(@PathVariable Long id) {
        return this.salonService.removeSalonFromFavourites(id)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<SalonSimpleDTO> updateSalon(@PathVariable Long id, @RequestBody SalonUpdateDTO salonUpdateDTO) {
        return this.salonService.updateSalon(id, salonUpdateDTO)
                .map(salon -> ResponseEntity.ok().body(salon.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SalonSimpleDTO> deleteSalonById(@PathVariable Long id) {
        Optional<Salon> salon = this.salonService.deleteSalonById(id);
        try {
            this.salonService.getSalonById(id);
            return ResponseEntity.badRequest().build();
        } catch (SalonNotFoundException exception) {
            return ResponseEntity.ok().body(salon.get().toDto());
        }
    }


    @PostMapping("/{id}/upload-image")
    public ResponseEntity<SalonSimpleDTO> uploadImage(@PathVariable Long id,
                                                      @RequestParam("image") MultipartFile image,
                                                      @RequestParam Integer imageNo) {
        try {
            Optional<Salon> salon = imageService.saveImage(id, image, imageNo);
            if (salon.isPresent())
                return ResponseEntity.ok().body(salon.get().toDto());
        } catch (IOException exception) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/upload-background-image")
    public ResponseEntity<SalonSimpleDTO> uploadBackgroundImage(@PathVariable Long id,
                                                                @RequestParam("image") MultipartFile image) {
        try {
            Optional<Salon> salon = imageService.saveBackgroundImage(id, image);
            if (salon.isPresent())
                return ResponseEntity.ok(salon.get().toDto());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
//            return ResponseEntity.status(500).body("Image upload failed: ${e.message}")
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/image/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id, @PathVariable Long imageId) {
        byte[] image = imageService.getImage(id, imageId);
        if (image != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        }
        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/{id}/image/{imageId}")
//    public ResponseEntity<ImageEntitySimpleDTO> getImage(@PathVariable Long id, @PathVariable Long imageId) {
//        Optional<ImageEntity> imageEntity = imageService.getImage(id, imageId);
//        return imageEntity.map(entity -> ResponseEntity.ok()
//                .body(entity.toDto())).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
