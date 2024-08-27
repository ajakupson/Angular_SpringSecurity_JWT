import { Component, ViewChild } from '@angular/core';
import { Book } from '../../models/book.interface';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { AxiosService } from '../../services/axios/axios.service';
import { SmitResponse } from '../../models/smit-response.interface';
import { FormsModule } from '@angular/forms';
import { FilterBooksPipe } from '../../pipes/filter.books.pipe';
import { DatePickerComponent } from '../date-picker/date-picker.component';
import { BookReservation } from '../../models/book.reservation.interface';
import { ToastrService } from 'ngx-toastr';
import { ModalService } from '../../services/modal/modal.service';
declare var $: any;

@Component({
  selector: 'app-borrowing-books',
  standalone: true,
  imports: [NgFor, FormsModule, FilterBooksPipe, DatePickerComponent, DatePipe, NgIf],
  templateUrl: './borrowing-books.component.html',
  styleUrl: './borrowing-books.component.css'
})
export class BorrowingBooksComponent {

  reservedBooks: Book[] = [];
  allBooks: Book[] = [];

  smitResponse: SmitResponse = {
    success: null,
    httpStatus: null,
    message: null,
    data: null
  }

  searchString: string = '';

  bookToReserve: Book = {
    uuid: null,
    title: null,
    author: null,
    bookReservations: [],
    user: null
  }

  bookToReserveDisabledDates: (string | null)[][] = [];

  bookToReserveStartDate: string = '';
  bookToReserveEndDate: string = '';

  @ViewChild(DatePickerComponent) datePickerComponent!: DatePickerComponent;

  constructor(
    private axiosService: AxiosService, 
    private toast: ToastrService,
    private modal: ModalService
  ) {

  }

  ngOnInit() {
    this.getReservedBooks();
    this.getOtherUsersBooks();
  }

  getReservedBooks() {
    this.axiosService.request(
      "GET",
      "/book/user/" + this.axiosService.getUserUuid() + "/reserved",
      null
    ).then(
      (response) => {
        this.smitResponse = response.data;
        if (this.smitResponse.success) {
          this.reservedBooks = this.smitResponse.data;
        }
      }
    );
  }

  getOtherUsersBooks() {
    this.axiosService.request(
      "GET",
      "/book/user/" + this.axiosService.getUserUuid() + "/books",
      null
    ).then(
      (response) => {
        this.smitResponse = response.data;
        if (this.smitResponse.success) {
          this.allBooks = this.smitResponse.data;
        }
      }
    );
  }

  getBookForReservation(book: Book) {
    this.axiosService.request(
      "GET",
      "/book/" + book.uuid,
      null
    ).then(
      (response) => {
        this.smitResponse = response.data;
        this.bookToReserve = this.smitResponse.data;

        this.bookToReserveDisabledDates = [];

        this.bookToReserve.bookReservations.forEach(reservation => {
          if (!reservation.isCancelled || !reservation.isReturned) {
            const reservedDates:[string | null, string | null] = [reservation.startDate, reservation.endDate];
            this.bookToReserveDisabledDates.push(reservedDates);
          }
        });

        this.datePickerComponent.initDatePicker(this.bookToReserveDisabledDates);
      }
    );
  }

  reserveBook() {
    let bookReservation: BookReservation = {
      uuid: null,
      bookUuid: this.bookToReserve.uuid,
      userUuid: this.axiosService.getUserUuid(),
      startDate: this.bookToReserveStartDate,
      endDate: this.bookToReserveEndDate,
      isReceived: false,
      isReturned: false,
      isCancelled: false,
      isSent: false
    }

    this.axiosService.request(
      "POST",
      "/book_reservation",
      bookReservation
    ).then(
      (response) => {
        this.smitResponse = response.data;

        if (this.smitResponse.success) {
          this.toast.success(this.smitResponse.message!, "Success!");

          this.getReservedBooks();
          this.modal.closeModal('btn-close-reserve-book-modal');
        } else {
          this.toast.error(this.smitResponse.message!, "Error!");
        }
      }
    );
  }

  markAsReceived(bookReservation: BookReservation) {
    bookReservation.isReceived = true;

    this.makeBookReservationRequest("PUT", bookReservation);
  }

  markAsReturned(bookReservation: BookReservation) {
    bookReservation.isReturned = true;

    this.makeBookReservationRequest("PUT", bookReservation);
  }

  markAsCancelled(bookReservation: BookReservation) {
    bookReservation.isCancelled = true;

    this.makeBookReservationRequest("PUT", bookReservation);
  }

  makeBookReservationRequest(method: string, bookReservation: BookReservation) {
    this.axiosService.request(
      method,
      "/book_reservation",
      bookReservation
    ).then(
      (response) => {
        this.smitResponse = response.data;

        if (this.smitResponse.success) {
          this.toast.success(this.smitResponse.message!, "Success!");
        } else {
          this.toast.error(this.smitResponse.message!, "Error!");
        }
      }
    );
  }

  onReservationStartDateChange(newDate: string): void {
    this.bookToReserveStartDate = newDate;
  }

  onReservationEndDateChange(newDate: string): void {
    this.bookToReserveEndDate = newDate;
  }

}
