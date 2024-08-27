export interface BookReservation {
    uuid: string | null;
    bookUuid: string | null;
    userUuid: string | null;
    startDate: string | null;
    endDate: string | null;
    isReceived: boolean;
    isReturned: boolean;
    isCancelled: boolean;
    isSent: boolean;
}