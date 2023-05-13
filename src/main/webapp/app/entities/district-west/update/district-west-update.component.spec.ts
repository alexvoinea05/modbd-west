import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DistrictWestFormService } from './district-west-form.service';
import { DistrictWestService } from '../service/district-west.service';
import { IDistrictWest } from '../district-west.model';

import { DistrictWestUpdateComponent } from './district-west-update.component';

describe('DistrictWest Management Update Component', () => {
  let comp: DistrictWestUpdateComponent;
  let fixture: ComponentFixture<DistrictWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let districtWestFormService: DistrictWestFormService;
  let districtWestService: DistrictWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DistrictWestUpdateComponent],
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
      .overrideTemplate(DistrictWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistrictWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    districtWestFormService = TestBed.inject(DistrictWestFormService);
    districtWestService = TestBed.inject(DistrictWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const districtWest: IDistrictWest = { id: 456 };

      activatedRoute.data = of({ districtWest });
      comp.ngOnInit();

      expect(comp.districtWest).toEqual(districtWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictWest>>();
      const districtWest = { id: 123 };
      jest.spyOn(districtWestFormService, 'getDistrictWest').mockReturnValue(districtWest);
      jest.spyOn(districtWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtWest }));
      saveSubject.complete();

      // THEN
      expect(districtWestFormService.getDistrictWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(districtWestService.update).toHaveBeenCalledWith(expect.objectContaining(districtWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictWest>>();
      const districtWest = { id: 123 };
      jest.spyOn(districtWestFormService, 'getDistrictWest').mockReturnValue({ id: null });
      jest.spyOn(districtWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: districtWest }));
      saveSubject.complete();

      // THEN
      expect(districtWestFormService.getDistrictWest).toHaveBeenCalled();
      expect(districtWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistrictWest>>();
      const districtWest = { id: 123 };
      jest.spyOn(districtWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ districtWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(districtWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
