import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LicenseWestFormService } from './license-west-form.service';
import { LicenseWestService } from '../service/license-west.service';
import { ILicenseWest } from '../license-west.model';

import { LicenseWestUpdateComponent } from './license-west-update.component';

describe('LicenseWest Management Update Component', () => {
  let comp: LicenseWestUpdateComponent;
  let fixture: ComponentFixture<LicenseWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let licenseWestFormService: LicenseWestFormService;
  let licenseWestService: LicenseWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LicenseWestUpdateComponent],
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
      .overrideTemplate(LicenseWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LicenseWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    licenseWestFormService = TestBed.inject(LicenseWestFormService);
    licenseWestService = TestBed.inject(LicenseWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const licenseWest: ILicenseWest = { id: 456 };

      activatedRoute.data = of({ licenseWest });
      comp.ngOnInit();

      expect(comp.licenseWest).toEqual(licenseWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseWest>>();
      const licenseWest = { id: 123 };
      jest.spyOn(licenseWestFormService, 'getLicenseWest').mockReturnValue(licenseWest);
      jest.spyOn(licenseWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licenseWest }));
      saveSubject.complete();

      // THEN
      expect(licenseWestFormService.getLicenseWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(licenseWestService.update).toHaveBeenCalledWith(expect.objectContaining(licenseWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseWest>>();
      const licenseWest = { id: 123 };
      jest.spyOn(licenseWestFormService, 'getLicenseWest').mockReturnValue({ id: null });
      jest.spyOn(licenseWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: licenseWest }));
      saveSubject.complete();

      // THEN
      expect(licenseWestFormService.getLicenseWest).toHaveBeenCalled();
      expect(licenseWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicenseWest>>();
      const licenseWest = { id: 123 };
      jest.spyOn(licenseWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ licenseWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(licenseWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
