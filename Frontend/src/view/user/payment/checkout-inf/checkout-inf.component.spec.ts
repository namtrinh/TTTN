import { ComponentFixture, TestBed } from '@angular/core/testing';

import {CheckoutInfComponent} from './checkout-inf.component';
import {HttpClientModule} from '@angular/common/http';

describe('CheckoutInfComponent', () => {
  let component: CheckoutInfComponent;
  let fixture: ComponentFixture<CheckoutInfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckoutInfComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CheckoutInfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
