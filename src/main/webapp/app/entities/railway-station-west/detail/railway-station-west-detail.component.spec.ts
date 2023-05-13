import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayStationWestDetailComponent } from './railway-station-west-detail.component';

describe('RailwayStationWest Management Detail Component', () => {
  let comp: RailwayStationWestDetailComponent;
  let fixture: ComponentFixture<RailwayStationWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayStationWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayStationWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayStationWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayStationWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayStationWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayStationWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
