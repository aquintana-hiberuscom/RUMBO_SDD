import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'registro',
    loadComponent: () =>
      import('./pages/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: '',
    redirectTo: 'registro',
    pathMatch: 'full'
  }
];
