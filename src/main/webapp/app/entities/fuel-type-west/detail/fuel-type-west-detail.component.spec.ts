import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FuelTypeWestDetailComponent } from './fuel-type-west-detail.component';

describe('FuelTypeWest Management Detail Component', () => {
  let comp: FuelTypeWestDetailComponent;
  let fixture: ComponentFixture<FuelTypeWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FuelTypeWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fuelTypeWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FuelTypeWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FuelTypeWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fuelTypeWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fuelTypeWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
