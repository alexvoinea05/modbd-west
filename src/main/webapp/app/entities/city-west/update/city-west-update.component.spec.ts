import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CityWestFormService } from './city-west-form.service';
import { CityWestService } from '../service/city-west.service';
import { ICityWest } from '../city-west.model';

import { CityWestUpdateComponent } from './city-west-update.component';

describe('CityWest Management Update Component', () => {
  let comp: CityWestUpdateComponent;
  let fixture: ComponentFixture<CityWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cityWestFormService: CityWestFormService;
  let cityWestService: CityWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CityWestUpdateComponent],
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
      .overrideTemplate(CityWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CityWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cityWestFormService = TestBed.inject(CityWestFormService);
    cityWestService = TestBed.inject(CityWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cityWest: ICityWest = { id: 456 };

      activatedRoute.data = of({ cityWest });
      comp.ngOnInit();

      expect(comp.cityWest).toEqual(cityWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityWest>>();
      const cityWest = { id: 123 };
      jest.spyOn(cityWestFormService, 'getCityWest').mockReturnValue(cityWest);
      jest.spyOn(cityWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cityWest }));
      saveSubject.complete();

      // THEN
      expect(cityWestFormService.getCityWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cityWestService.update).toHaveBeenCalledWith(expect.objectContaining(cityWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityWest>>();
      const cityWest = { id: 123 };
      jest.spyOn(cityWestFormService, 'getCityWest').mockReturnValue({ id: null });
      jest.spyOn(cityWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cityWest }));
      saveSubject.complete();

      // THEN
      expect(cityWestFormService.getCityWest).toHaveBeenCalled();
      expect(cityWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICityWest>>();
      const cityWest = { id: 123 };
      jest.spyOn(cityWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cityWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cityWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
