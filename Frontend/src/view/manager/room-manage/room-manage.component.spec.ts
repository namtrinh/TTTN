import { ComponentFixture, TestBed } from '@angular/core/testing';

import {RoomManageComponent} from './room-manage.component';
import {HttpClientModule} from '@angular/common/http';

describe('RoomManageComponent', () => {
  let component: RoomManageComponent;
  let fixture: ComponentFixture<RoomManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomManageComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RoomManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
