import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ResetPasswordComponent} from './reset-password.component';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let fixture: ComponentFixture<ResetPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResetPasswordComponent, HttpClientModule],
      providers:[
        {provide:ActivatedRoute}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ResetPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
