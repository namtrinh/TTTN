import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

// Nếu bạn đang dùng Capacitor 4+, dùng @ionic/pwa-elements
import { defineCustomElements } from '@ionic/pwa-elements/loader';

// Định nghĩa các phần tử Web Elements (camera modal, etc.)
defineCustomElements(window);

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
