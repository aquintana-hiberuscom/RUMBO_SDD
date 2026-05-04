import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ApiResponse, LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly apiUrl = 'http://localhost:8080/api/v1/auth';
  private readonly TOKEN_KEY = 'rumbo_token';

  constructor(private http: HttpClient) {}

  register(request: RegisterRequest): Observable<ApiResponse<RegisterResponse>> {
    return this.http
      .post<ApiResponse<RegisterResponse>>(`${this.apiUrl}/register`, request)
      .pipe(tap(response => {
        if (response.success && response.data?.token) {
          this.saveToken(response.data.token);
        }
      }));
  }

  login(request: LoginRequest): Observable<ApiResponse<LoginResponse>> {
    return this.http
      .post<ApiResponse<LoginResponse>>(`${this.apiUrl}/login`, request)
      .pipe(tap(response => {
        if (response.success && response.data?.token) {
          this.saveToken(response.data.token);
        }
      }));
  }

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
