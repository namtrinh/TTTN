import { ComponentFixture, TestBed } from '@angular/core/testing';
import {PaymentSuccessComponent} from './payment-success.component';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';


describe('PaymentSuccessComponent', () => {
  let component: PaymentSuccessComponent;
  let fixture: ComponentFixture<PaymentSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentSuccessComponent, HttpClientModule],
      providers:[
        {
          provide:ActivatedRoute
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(PaymentSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
