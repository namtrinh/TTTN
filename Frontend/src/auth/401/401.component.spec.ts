import { ComponentFixture, TestBed } from '@angular/core/testing';
import {Component} from '@angular/core';
import {_401Component} from './401.component';
import {HttpClientModule} from '@angular/common/http';


describe('_401Component', () => {
  let component: _401Component;
  let fixture: ComponentFixture<_401Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [_401Component, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(_401Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
