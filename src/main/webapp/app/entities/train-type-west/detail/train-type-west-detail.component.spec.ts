import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainTypeWestDetailComponent } from './train-type-west-detail.component';

describe('TrainTypeWest Management Detail Component', () => {
  let comp: TrainTypeWestDetailComponent;
  let fixture: ComponentFixture<TrainTypeWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainTypeWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainTypeWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainTypeWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainTypeWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainTypeWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainTypeWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
