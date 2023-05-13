import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyWestFormService } from './company-west-form.service';
import { CompanyWestService } from '../service/company-west.service';
import { ICompanyWest } from '../company-west.model';

import { CompanyWestUpdateComponent } from './company-west-update.component';

describe('CompanyWest Management Update Component', () => {
  let comp: CompanyWestUpdateComponent;
  let fixture: ComponentFixture<CompanyWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyWestFormService: CompanyWestFormService;
  let companyWestService: CompanyWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyWestUpdateComponent],
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
      .overrideTemplate(CompanyWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyWestFormService = TestBed.inject(CompanyWestFormService);
    companyWestService = TestBed.inject(CompanyWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const companyWest: ICompanyWest = { id: 456 };

      activatedRoute.data = of({ companyWest });
      comp.ngOnInit();

      expect(comp.companyWest).toEqual(companyWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyWest>>();
      const companyWest = { id: 123 };
      jest.spyOn(companyWestFormService, 'getCompanyWest').mockReturnValue(companyWest);
      jest.spyOn(companyWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyWest }));
      saveSubject.complete();

      // THEN
      expect(companyWestFormService.getCompanyWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyWestService.update).toHaveBeenCalledWith(expect.objectContaining(companyWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyWest>>();
      const companyWest = { id: 123 };
      jest.spyOn(companyWestFormService, 'getCompanyWest').mockReturnValue({ id: null });
      jest.spyOn(companyWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyWest }));
      saveSubject.complete();

      // THEN
      expect(companyWestFormService.getCompanyWest).toHaveBeenCalled();
      expect(companyWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyWest>>();
      const companyWest = { id: 123 };
      jest.spyOn(companyWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
