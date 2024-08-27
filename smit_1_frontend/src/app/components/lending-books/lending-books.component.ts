import { Component } from '@angular/core';
import { Book } from '../../models/book.interface';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AxiosService } from '../../services/axios/axios.service';
import { SmitResponse } from '../../models/smit-response.interface';
import { NgFor, NgIf } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ModalService } from '../../services/modal/modal.service';
import { BookReservation } from '../../models/book.reservation.interface';

@Component({
  selector: 'app-lending-books',
  standalone: true,
  imports: [FormsModule, NgFor, ReactiveFormsModule, NgIf],
  templateUrl: './lending-books.component.html',
  styleUrl: './lending-books.component.css'
})
export class LendingBooksComponent {

  book: Book = {
    uuid: null,
    title: null,
    author: null,
    bookReservations: [],
    user: null
  }

  books: Book[] = [];

  smitResponse: SmitResponse = {
    success: null,
    httpStatus: null,
    message: null,
    data: null
  }

  bookForm!: FormGroup;

  constructor(
    private axiosService: AxiosService, 
    private fb: FormBuilder,
    private toast: ToastrService,
    private modal: ModalService
  ) {
    this.bookForm = this.fb.group({
      title: [this.book.title, [Validators.required]],
      author: [this.book.author, Validators.required]
    });
  }

  ngOnInit() {
    this.getBooks();
  }

  getBooks() {
    this.axiosService.request(
      "GET",
      "/book/user/" + this.axiosService.getUserUuid(),
      null
    ).then(
      (response) => {
        this.smitResponse = response.data;
        if (this.smitResponse.success) {
          this.books = this.smitResponse.data;
        }
      }
    );
  }

  saveBook() {
    const method = !!this.book.uuid ? "PUT" : "POST";

    this.axiosService.request(
      method,
      "/book/user/"  + this.axiosService.getUserUuid(),
      this.book
    ).then(
      (response) => {
        this.smitResponse = response.data;
        if (this.smitResponse.success) {
          this.getBooks();

          this.toast.success(this.smitResponse.message!, "Success!");
          this.modal.closeModal('btn-close-add-edit-book-modal');
        } else {
          this.toast.error(this.smitResponse.message!, "Error!");
        }
      }
    );
  }

  setBookForEdit(book: Book): void {
    this.book = book;
  }

  markAsReceived(bookReservation: BookReservation) {
    bookReservation.isReceived = true;

    this.makeBookReservationRequest("PUT", bookReservation);
  }

  markAsSent(bookReservation: BookReservation) {
    bookReservation.isSent = true;

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

  deleteBook(book: Book) {
    if (confirm("Are you sure?")) {
      this.axiosService.request(
        "DELETE",
        "/book/" + book.uuid,
        null
      ).then(
        (response) => {
          this.smitResponse = response.data;
  
          if (this.smitResponse.success) {
            this.toast.success(this.smitResponse.message!, "Success!");
            this.getBooks();
          } else {
            this.toast.error(this.smitResponse.message!, "Error!");
          }
        }
      );
    }
  }
}