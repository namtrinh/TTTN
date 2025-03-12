import { ComponentFixture, TestBed } from '@angular/core/testing';

import {DashboardManageComponent} from './dashboard-manage.component';

describe('DashboardManageComponent', () => {
  let component: DashboardManageComponent;
  let fixture: ComponentFixture<DashboardManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardManageComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DashboardManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
