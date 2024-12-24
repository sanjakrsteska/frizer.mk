import { ImagePosition } from "../enums/ImagePosition.enum";

export interface Image{
    id?: number,
    image : Uint8Array,
    salonId: number,
    isBackgroundImage: false,
    imagePositon?: ImagePosition
}