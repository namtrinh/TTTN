import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ShowtimeManageComponent} from './showtime-manage.component';
import {HttpClientModule} from '@angular/common/http';


describe('ShowtimeManageComponent', () => {
  let component: ShowtimeManageComponent;
  let fixture: ComponentFixture<ShowtimeManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowtimeManageComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ShowtimeManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
