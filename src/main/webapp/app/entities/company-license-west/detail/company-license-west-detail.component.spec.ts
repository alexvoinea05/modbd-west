import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyLicenseWestDetailComponent } from './company-license-west-detail.component';

describe('CompanyLicenseWest Management Detail Component', () => {
  let comp: CompanyLicenseWestDetailComponent;
  let fixture: ComponentFixture<CompanyLicenseWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyLicenseWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyLicenseWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyLicenseWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyLicenseWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyLicenseWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyLicenseWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
