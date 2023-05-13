import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JourneyWestDetailComponent } from './journey-west-detail.component';

describe('JourneyWest Management Detail Component', () => {
  let comp: JourneyWestDetailComponent;
  let fixture: ComponentFixture<JourneyWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JourneyWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ journeyWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JourneyWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JourneyWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load journeyWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.journeyWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
