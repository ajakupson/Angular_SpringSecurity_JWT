import { Component } from '@angular/core';
import { AxiosService } from '../../services/axios/axios.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  userUuid: string | null = null;

  constructor(private axiosService: AxiosService) {

  }
  showNavBar(): boolean {
    return this.axiosService.getAuthToken() !== null;
  }
}
