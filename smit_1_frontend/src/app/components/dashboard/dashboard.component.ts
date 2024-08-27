import { Component } from '@angular/core';
import { AxiosService } from '../../services/axios/axios.service';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, NavbarComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  data: string[] = [];

  constructor(private axiosService: AxiosService) {

  }

  ngOnInit(): void  {

  }

}
