import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePickerEvent } from '../../models/date.picker.event.interface';
declare var $: any;

@Component({
  selector: 'app-date-picker',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './date-picker.component.html',
  styleUrl: './date-picker.component.css'
})
export class DatePickerComponent implements AfterViewInit {

  startDate: string = '';
  @Output() startDateChange = new EventEmitter<string>();

  endDate: string = '';
  @Output() endDateChange = new EventEmitter<string>();

  ngAfterViewInit(): void {
    this.initDatePicker([]);
  }

  initDatePicker(disabledDates: (string | null)[][]) {
    this.startDate = '';
    this.endDate = '';

    const startDatePicker = $('#datepickerStart');
    const endDatePicker = $('#datepickerEnd');

    startDatePicker.datepicker('destroy');
    endDatePicker.datepicker('destroy');

    const disableRanges = (dates: any[]) => {
      let disabled: Array<Date> = [];
      dates.forEach(range => {
        if (Array.isArray(range)) {
          let [start, end] = range;
          let current = new Date(start);
          while (current <= new Date(end)) {
            disabled.push(new Date(current));
            current.setDate(current.getDate() + 1);
          }
        } else {
          disabled.push(new Date(range));
        }
      });
      return disabled;
    };

    const disabledDateArray = disableRanges(disabledDates);

    startDatePicker.datepicker({
      format: 'yyyy-mm-dd',
      language: 'ru',
      autoclose: true,
      todayHighlight: true,
      datesDisabled: disabledDateArray,
    }).on('changeDate', (e: DatePickerEvent) => {
      this.startDate = e.format('yyyy-mm-dd');
      this.startDateChange.emit(this.startDate);

      $('#endDatePicker').datepicker('setStartDate', e.date);
    });

    startDatePicker.on('keydown paste', function(e: Event) {
      e.preventDefault();
      return false;
    });

    endDatePicker.datepicker({
      format: 'yyyy-mm-dd',
      language: 'ru',
      autoclose: true,
      todayHighlight: true,
      datesDisabled: disabledDateArray,
    }).on('changeDate', (e: DatePickerEvent) => {
      const formattedDate = e.format('yyyy-mm-dd');
      this.endDate = formattedDate;
      this.endDateChange.emit(this.endDate);
  
      $('#startDatePicker').datepicker('setEndDate', e.date);
    });

    endDatePicker.on('keydown paste', function(e: Event) {
      e.preventDefault();
      return false;
    });
  }
}
