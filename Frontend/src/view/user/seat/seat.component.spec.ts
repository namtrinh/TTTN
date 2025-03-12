import { ComponentFixture, TestBed } from '@angular/core/testing';
import {SeatComponent} from './seat.component';
import {HttpClientModule} from '@angular/common/http';


describe('SeatComponent', () => {
  let component: SeatComponent;
  let fixture: ComponentFixture<SeatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeatComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SeatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
