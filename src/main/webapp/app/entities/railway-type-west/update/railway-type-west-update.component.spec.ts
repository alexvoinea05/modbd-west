import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayTypeWestFormService } from './railway-type-west-form.service';
import { RailwayTypeWestService } from '../service/railway-type-west.service';
import { IRailwayTypeWest } from '../railway-type-west.model';

import { RailwayTypeWestUpdateComponent } from './railway-type-west-update.component';

describe('RailwayTypeWest Management Update Component', () => {
  let comp: RailwayTypeWestUpdateComponent;
  let fixture: ComponentFixture<RailwayTypeWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayTypeWestFormService: RailwayTypeWestFormService;
  let railwayTypeWestService: RailwayTypeWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayTypeWestUpdateComponent],
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
      .overrideTemplate(RailwayTypeWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayTypeWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayTypeWestFormService = TestBed.inject(RailwayTypeWestFormService);
    railwayTypeWestService = TestBed.inject(RailwayTypeWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const railwayTypeWest: IRailwayTypeWest = { id: 456 };

      activatedRoute.data = of({ railwayTypeWest });
      comp.ngOnInit();

      expect(comp.railwayTypeWest).toEqual(railwayTypeWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeWest>>();
      const railwayTypeWest = { id: 123 };
      jest.spyOn(railwayTypeWestFormService, 'getRailwayTypeWest').mockReturnValue(railwayTypeWest);
      jest.spyOn(railwayTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayTypeWest }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeWestFormService.getRailwayTypeWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayTypeWestService.update).toHaveBeenCalledWith(expect.objectContaining(railwayTypeWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeWest>>();
      const railwayTypeWest = { id: 123 };
      jest.spyOn(railwayTypeWestFormService, 'getRailwayTypeWest').mockReturnValue({ id: null });
      jest.spyOn(railwayTypeWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayTypeWest }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeWestFormService.getRailwayTypeWest).toHaveBeenCalled();
      expect(railwayTypeWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayTypeWest>>();
      const railwayTypeWest = { id: 123 };
      jest.spyOn(railwayTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayTypeWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
