import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FilmDetailComponent} from './film-detail.component';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';



describe('FilmDetailComponent', () => {
  let component: FilmDetailComponent;
  let fixture: ComponentFixture<FilmDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmDetailComponent, HttpClientModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              params: { movieTitle: 'Inception' }  // Mock giá trị movieTitle
            }
          }
        }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FilmDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
