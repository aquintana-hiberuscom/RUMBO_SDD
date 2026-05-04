import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { ApiResponse, RegisterResponse } from '../models/auth.model';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should register user and save token on success', () => {
    const mockResponse: ApiResponse<RegisterResponse> = {
      success: true,
      data: { userId: 1, email: 'ana@example.com', nombre: 'Ana', apellidos: 'García', token: 'jwt.token' },
      message: 'Usuario registrado correctamente',
      timestamp: new Date().toISOString()
    };

    service.register({ email: 'ana@example.com', nombre: 'Ana', apellidos: 'García', edad: 28, password: 'pass1234' })
      .subscribe(response => {
        expect(response.success).toBeTrue();
        expect(service.getToken()).toBe('jwt.token');
      });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/auth/register');
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should not save token when registration fails', () => {
    const errorResponse: ApiResponse<RegisterResponse> = {
      success: false, data: null as any, message: 'Email ya registrado', timestamp: new Date().toISOString()
    };

    service.register({ email: 'test@example.com', nombre: 'Test', apellidos: 'User', edad: 25, password: 'pass1234' })
      .subscribe({
        error: () => {
          expect(service.getToken()).toBeNull();
        }
      });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/auth/register');
    req.flush(errorResponse, { status: 409, statusText: 'Conflict' });
  });
});
