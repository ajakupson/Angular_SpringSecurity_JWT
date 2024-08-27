export interface SmitResponse {
    success: boolean | null;
    httpStatus: string | null;
    message: string | null;
    data: any | null;
}