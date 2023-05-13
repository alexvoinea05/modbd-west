import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainWestDetailComponent } from './train-west-detail.component';

describe('TrainWest Management Detail Component', () => {
  let comp: TrainWestDetailComponent;
  let fixture: ComponentFixture<TrainWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
