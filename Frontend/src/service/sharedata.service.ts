import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  private dataSource = new BehaviorSubject<any>(JSON.parse(sessionStorage.getItem('sharedData') || '{}'));
  currentData = this.dataSource.asObservable();

  constructor() {}

  changeData(data: any) {
    this.dataSource.next(data);
    sessionStorage.setItem('sharedData', JSON.stringify(data));
  }
}
