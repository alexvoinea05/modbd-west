import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CityWestDetailComponent } from './city-west-detail.component';

describe('CityWest Management Detail Component', () => {
  let comp: CityWestDetailComponent;
  let fixture: ComponentFixture<CityWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CityWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cityWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CityWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CityWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cityWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cityWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
