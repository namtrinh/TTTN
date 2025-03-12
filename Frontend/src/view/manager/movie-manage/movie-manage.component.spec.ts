import { ComponentFixture, TestBed } from '@angular/core/testing';
import {MovieManageComponent} from './movie-manage.component';
import {HttpClientModule} from '@angular/common/http';



describe('MovieManageComponent', () => {
  let component: MovieManageComponent;
  let fixture: ComponentFixture<MovieManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieManageComponent, HttpClientModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(MovieManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
