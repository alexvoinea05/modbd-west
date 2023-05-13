import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JourneyStatusWestDetailComponent } from './journey-status-west-detail.component';

describe('JourneyStatusWest Management Detail Component', () => {
  let comp: JourneyStatusWestDetailComponent;
  let fixture: ComponentFixture<JourneyStatusWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JourneyStatusWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ journeyStatusWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JourneyStatusWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JourneyStatusWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load journeyStatusWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.journeyStatusWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
