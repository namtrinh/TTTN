import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ViewManageComponent} from './view-manage.component';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';


describe('ViewManageComponent', () => {
  let component: ViewManageComponent;
  let fixture: ComponentFixture<ViewManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewManageComponent, HttpClientModule],
      providers:[
        {
          provide:ActivatedRoute
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
