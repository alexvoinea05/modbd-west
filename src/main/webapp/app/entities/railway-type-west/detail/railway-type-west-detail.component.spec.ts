import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayTypeWestDetailComponent } from './railway-type-west-detail.component';

describe('RailwayTypeWest Management Detail Component', () => {
  let comp: RailwayTypeWestDetailComponent;
  let fixture: ComponentFixture<RailwayTypeWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayTypeWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayTypeWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayTypeWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayTypeWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayTypeWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayTypeWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
