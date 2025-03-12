import { TestBed } from '@angular/core/testing';
import {SharedDataService} from './sharedata.service';
import {HttpClientModule} from '@angular/common/http';




describe('SharedDataService', () => {
  let service: SharedDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[HttpClientModule]});
    service = TestBed.inject(SharedDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
