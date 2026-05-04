import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  form: FormGroup;
  isLoading = false;
  serverError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellidos: ['', [Validators.required, Validators.minLength(2)]],
      edad: [null, [Validators.required, Validators.min(1), Validators.max(120)]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.serverError = null;

    this.authService.register(this.form.value).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/perfil']);
      },
      error: (err) => {
        this.isLoading = false;
        this.serverError = err.error?.message ?? 'Error al registrar el usuario. Inténtalo de nuevo.';
      }
    });
  }

  isFieldInvalid(field: string): boolean {
    const control = this.form.get(field);
    return !!(control?.invalid && control?.touched);
  }

  getFieldError(field: string): string {
    const control = this.form.get(field);
    if (!control?.errors) return '';

    if (control.errors['required']) return `Este campo es obligatorio`;
    if (control.errors['email']) return `El email no tiene un formato válido`;
    if (control.errors['minlength']) return `Mínimo ${control.errors['minlength'].requiredLength} caracteres`;
    if (control.errors['min']) return `Debe ser un número positivo`;
    if (control.errors['max']) return `La edad no es válida`;
    return '';
  }
}
