import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.baseURL = "http://localhost:8080/api";
    axios.defaults.headers.post["Content-Type"] = "application/json";
   }

   getAuthToken(): string | null {
    const token = window.localStorage.getItem("auth_token");

    if (!token) {
      return null;
    }

    if (this.isAuthTokenExpired(token)) {
      window.localStorage.removeItem("auth_token");
      window.localStorage.removeItem("user_uuid");
      return null;
    }

    return token;

  }

  isAuthTokenExpired(token: string | null) {
    if (!token) {
      return true;
    }

    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiry = payload.exp;
    const now = Math.floor(Date.now() / 1000);
    
    return now > expiry;
}

  setAuthToken(token: string | null): void {
    if (!!token) {
      window.localStorage.setItem("auth_token", token);

      return;
    }

    window.localStorage.removeItem("auth_token");
    window.localStorage.removeItem("user_uuid");
  }

  setUserUuid(uuid: string | null): void {
    if (!!uuid) {
      window.localStorage.setItem("user_uuid", uuid);
    }
  }

  getUserUuid(): string | null {
    return window.localStorage.getItem("user_uuid");
  }

   request(method: string, url: string, data: any): Promise<any> {
    let headers: any = {};

    if (this.getAuthToken() !== null) {
        headers = {"Authorization": "Bearer " + this.getAuthToken()};
    }

    return axios({
      headers: headers,
      method: method,
      url: url,
      data: data
    });
   }
}
