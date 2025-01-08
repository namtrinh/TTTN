import { Injectable } from '@angular/core';
import { CanActivate, Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouteGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {

    // Kiểm tra lịch sử điều hướng
    const previousUrl = this.router.getCurrentNavigation()?.previousNavigation?.finalUrl;

    if (previousUrl) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
