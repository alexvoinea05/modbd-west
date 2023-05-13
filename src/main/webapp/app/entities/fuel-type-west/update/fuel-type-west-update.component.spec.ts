import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FuelTypeWestFormService } from './fuel-type-west-form.service';
import { FuelTypeWestService } from '../service/fuel-type-west.service';
import { IFuelTypeWest } from '../fuel-type-west.model';

import { FuelTypeWestUpdateComponent } from './fuel-type-west-update.component';

describe('FuelTypeWest Management Update Component', () => {
  let comp: FuelTypeWestUpdateComponent;
  let fixture: ComponentFixture<FuelTypeWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTypeWestFormService: FuelTypeWestFormService;
  let fuelTypeWestService: FuelTypeWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FuelTypeWestUpdateComponent],
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
      .overrideTemplate(FuelTypeWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTypeWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTypeWestFormService = TestBed.inject(FuelTypeWestFormService);
    fuelTypeWestService = TestBed.inject(FuelTypeWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fuelTypeWest: IFuelTypeWest = { id: 456 };

      activatedRoute.data = of({ fuelTypeWest });
      comp.ngOnInit();

      expect(comp.fuelTypeWest).toEqual(fuelTypeWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeWest>>();
      const fuelTypeWest = { id: 123 };
      jest.spyOn(fuelTypeWestFormService, 'getFuelTypeWest').mockReturnValue(fuelTypeWest);
      jest.spyOn(fuelTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTypeWest }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeWestFormService.getFuelTypeWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTypeWestService.update).toHaveBeenCalledWith(expect.objectContaining(fuelTypeWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeWest>>();
      const fuelTypeWest = { id: 123 };
      jest.spyOn(fuelTypeWestFormService, 'getFuelTypeWest').mockReturnValue({ id: null });
      jest.spyOn(fuelTypeWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTypeWest }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeWestFormService.getFuelTypeWest).toHaveBeenCalled();
      expect(fuelTypeWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTypeWest>>();
      const fuelTypeWest = { id: 123 };
      jest.spyOn(fuelTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTypeWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
