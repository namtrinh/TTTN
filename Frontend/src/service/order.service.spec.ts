import { TestBed } from '@angular/core/testing';
import {OrderService} from './order.service';
import {HttpClientModule} from '@angular/common/http';



describe('OrderService', () => {
  let service: OrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[HttpClientModule]});
    service = TestBed.inject(OrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
