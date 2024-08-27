import { BookReservation } from "./book.reservation.interface";
import { User } from "./user.interface";

export interface Book {
    uuid: string | null;
    title: string | null;
    author: string | null;
    bookReservations: BookReservation[];
    user: User | null;
}