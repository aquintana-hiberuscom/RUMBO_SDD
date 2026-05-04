export interface RegisterRequest {
  email: string;
  nombre: string;
  apellidos: string;
  edad: number;
  password: string;
}

export interface RegisterResponse {
  userId: string;
  email: string;
  nombre: string;
  apellidos: string;
  token: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  userId: string;
  email: string;
  nombre: string;
  apellidos: string;
  token: string;
}

export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message: string;
  timestamp: string;
}
