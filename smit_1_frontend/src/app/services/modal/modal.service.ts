import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor() { }

  closeModal(modalBtnCloseId: string) {
    document.getElementById(modalBtnCloseId)?.click();
  }
}
