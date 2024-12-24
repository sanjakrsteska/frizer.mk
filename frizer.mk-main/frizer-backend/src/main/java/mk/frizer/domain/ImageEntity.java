package mk.frizer.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.enums.ImagePosition;

@Entity
@Data
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    private Long salonId;
    private boolean isBackgroundImage;

    @Nullable
    private ImagePosition imagePosition;

    public ImageEntity(byte[] image, Long salonId, boolean isBackgroundImage) {
        this.image = image;
        this.salonId = salonId;
        this.isBackgroundImage = isBackgroundImage;
        this.imagePosition = null;
    }

    public ImageEntity(byte[] image, Long salonId, ImagePosition imagePosition) {
        this.image = image;
        this.salonId = salonId;
        this.isBackgroundImage = false;
        this.imagePosition = imagePosition;
    }
}



