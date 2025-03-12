import { TestBed } from '@angular/core/testing';
import {SeatService} from './seat.service';
import {HttpClientModule} from '@angular/common/http';



describe('SeatService', () => {
  let service: SeatService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[HttpClientModule]});
    service = TestBed.inject(SeatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
