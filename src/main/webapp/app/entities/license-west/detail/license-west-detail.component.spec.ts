import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LicenseWestDetailComponent } from './license-west-detail.component';

describe('LicenseWest Management Detail Component', () => {
  let comp: LicenseWestDetailComponent;
  let fixture: ComponentFixture<LicenseWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LicenseWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ licenseWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LicenseWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LicenseWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load licenseWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.licenseWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
