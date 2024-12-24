import { Coordinate } from "./Coordinate.interface";

export interface City{
    name: string,
    salonsIdsInCity?: number[],
    coordinate?: Coordinate
}