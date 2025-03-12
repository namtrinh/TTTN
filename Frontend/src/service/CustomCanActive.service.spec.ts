import { TestBed } from '@angular/core/testing';
import {CustomCanActiveService} from './CustomCanActive.service';
import {HttpClientModule} from '@angular/common/http';



describe('CustomCanActiveService', () => {
  let service: CustomCanActiveService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[HttpClientModule]});
    service = TestBed.inject(CustomCanActiveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
