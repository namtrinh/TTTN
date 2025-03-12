import { TestBed } from '@angular/core/testing';
import {ShowtimeService} from './showtime.service';
import {HttpClientModule} from '@angular/common/http';




describe('ShowtimeService', () => {
  let service: ShowtimeService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[HttpClientModule]});
    service = TestBed.inject(ShowtimeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
