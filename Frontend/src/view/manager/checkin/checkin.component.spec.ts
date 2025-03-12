import { ComponentFixture, TestBed } from '@angular/core/testing';
import {CheckinComponent} from './checkin.component';
import {HttpClientModule} from '@angular/common/http';


describe('CheckinComponent', () => {
  let component: CheckinComponent;
  let fixture: ComponentFixture<CheckinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckinComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CheckinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
