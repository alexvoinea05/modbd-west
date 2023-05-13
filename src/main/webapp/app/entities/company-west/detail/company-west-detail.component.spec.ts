import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyWestDetailComponent } from './company-west-detail.component';

describe('CompanyWest Management Detail Component', () => {
  let comp: CompanyWestDetailComponent;
  let fixture: ComponentFixture<CompanyWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
