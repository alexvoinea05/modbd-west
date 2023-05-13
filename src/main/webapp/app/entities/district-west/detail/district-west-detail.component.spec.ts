import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DistrictWestDetailComponent } from './district-west-detail.component';

describe('DistrictWest Management Detail Component', () => {
  let comp: DistrictWestDetailComponent;
  let fixture: ComponentFixture<DistrictWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DistrictWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ districtWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DistrictWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DistrictWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load districtWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.districtWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
