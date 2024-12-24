package mk.frizer.domain.enums;

public enum ImagePosition {
    FIRST, SECOND, THIRD, FOURTH;

    public static ImagePosition fromInt(int imageNo) {
        ImagePosition[] positions = ImagePosition.values();
        if (imageNo > 0 && imageNo <= positions.length) {
            return positions[imageNo - 1];
        } else {
            throw new IllegalArgumentException("Invalid image number: " + imageNo);
        }
    }
}