export interface DatePickerEvent {
    date: Date;
    format(formatStr: string): string;
}