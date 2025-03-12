import { ComponentFixture, TestBed } from '@angular/core/testing';
import {LoginComponent} from './login.component';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent, HttpClientModule],
      providers:[
        {
          provide:ActivatedRoute
        }
      ]

    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
