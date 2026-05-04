import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  template: `
    <div style="display:flex;justify-content:center;align-items:center;min-height:100vh;flex-direction:column;gap:1rem;font-family:sans-serif;">
      <h1>Panel principal</h1>
      <p style="color:#6b7280">Aquí irá el dashboard del usuario (US-005, US-006, US-007)</p>
    </div>
  `
})
export class HomeComponent {}
