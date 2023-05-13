import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyLicenseWestFormService } from './company-license-west-form.service';
import { CompanyLicenseWestService } from '../service/company-license-west.service';
import { ICompanyLicenseWest } from '../company-license-west.model';
import { ICompanyWest } from 'app/entities/company-west/company-west.model';
import { CompanyWestService } from 'app/entities/company-west/service/company-west.service';
import { ILicenseWest } from 'app/entities/license-west/license-west.model';
import { LicenseWestService } from 'app/entities/license-west/service/license-west.service';

import { CompanyLicenseWestUpdateComponent } from './company-license-west-update.component';

describe('CompanyLicenseWest Management Update Component', () => {
  let comp: CompanyLicenseWestUpdateComponent;
  let fixture: ComponentFixture<CompanyLicenseWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyLicenseWestFormService: CompanyLicenseWestFormService;
  let companyLicenseWestService: CompanyLicenseWestService;
  let companyWestService: CompanyWestService;
  let licenseWestService: LicenseWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyLicenseWestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CompanyLicenseWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyLicenseWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyLicenseWestFormService = TestBed.inject(CompanyLicenseWestFormService);
    companyLicenseWestService = TestBed.inject(CompanyLicenseWestService);
    companyWestService = TestBed.inject(CompanyWestService);
    licenseWestService = TestBed.inject(LicenseWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CompanyWest query and add missing value', () => {
      const companyLicenseWest: ICompanyLicenseWest = { id: 456 };
      const idCompany: ICompanyWest = { id: 63248 };
      companyLicenseWest.idCompany = idCompany;

      const companyWestCollection: ICompanyWest[] = [{ id: 85061 }];
      jest.spyOn(companyWestService, 'query').mockReturnValue(of(new HttpResponse({ body: companyWestCollection })));
      const additionalCompanyWests = [idCompany];
      const expectedCollection: ICompanyWest[] = [...additionalCompanyWests, ...companyWestCollection];
      jest.spyOn(companyWestService, 'addCompanyWestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyLicenseWest });
      comp.ngOnInit();

      expect(companyWestService.query).toHaveBeenCalled();
      expect(companyWestService.addCompanyWestToCollectionIfMissing).toHaveBeenCalledWith(
        companyWestCollection,
        ...additionalCompanyWests.map(expect.objectContaining)
      );
      expect(comp.companyWestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LicenseWest query and add missing value', () => {
      const companyLicenseWest: ICompanyLicenseWest = { id: 456 };
      const idLicense: ILicenseWest = { id: 18155 };
      companyLicenseWest.idLicense = idLicense;

      const licenseWestCollection: ILicenseWest[] = [{ id: 66401 }];
      jest.spyOn(licenseWestService, 'query').mockReturnValue(of(new HttpResponse({ body: licenseWestCollection })));
      const additionalLicenseWests = [idLicense];
      const expectedCollection: ILicenseWest[] = [...additionalLicenseWests, ...licenseWestCollection];
      jest.spyOn(licenseWestService, 'addLicenseWestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyLicenseWest });
      comp.ngOnInit();

      expect(licenseWestService.query).toHaveBeenCalled();
      expect(licenseWestService.addLicenseWestToCollectionIfMissing).toHaveBeenCalledWith(
        licenseWestCollection,
        ...additionalLicenseWests.map(expect.objectContaining)
      );
      expect(comp.licenseWestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const companyLicenseWest: ICompanyLicenseWest = { id: 456 };
      const idCompany: ICompanyWest = { id: 29639 };
      companyLicenseWest.idCompany = idCompany;
      const idLicense: ILicenseWest = { id: 18428 };
      companyLicenseWest.idLicense = idLicense;

      activatedRoute.data = of({ companyLicenseWest });
      comp.ngOnInit();

      expect(comp.companyWestsSharedCollection).toContain(idCompany);
      expect(comp.licenseWestsSharedCollection).toContain(idLicense);
      expect(comp.companyLicenseWest).toEqual(companyLicenseWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseWest>>();
      const companyLicenseWest = { id: 123 };
      jest.spyOn(companyLicenseWestFormService, 'getCompanyLicenseWest').mockReturnValue(companyLicenseWest);
      jest.spyOn(companyLicenseWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyLicenseWest }));
      saveSubject.complete();

      // THEN
      expect(companyLicenseWestFormService.getCompanyLicenseWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyLicenseWestService.update).toHaveBeenCalledWith(expect.objectContaining(companyLicenseWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseWest>>();
      const companyLicenseWest = { id: 123 };
      jest.spyOn(companyLicenseWestFormService, 'getCompanyLicenseWest').mockReturnValue({ id: null });
      jest.spyOn(companyLicenseWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyLicenseWest }));
      saveSubject.complete();

      // THEN
      expect(companyLicenseWestFormService.getCompanyLicenseWest).toHaveBeenCalled();
      expect(companyLicenseWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyLicenseWest>>();
      const companyLicenseWest = { id: 123 };
      jest.spyOn(companyLicenseWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyLicenseWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyLicenseWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompanyWest', () => {
      it('Should forward to companyWestService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyWestService, 'compareCompanyWest');
        comp.compareCompanyWest(entity, entity2);
        expect(companyWestService.compareCompanyWest).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLicenseWest', () => {
      it('Should forward to licenseWestService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(licenseWestService, 'compareLicenseWest');
        comp.compareLicenseWest(entity, entity2);
        expect(licenseWestService.compareLicenseWest).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
