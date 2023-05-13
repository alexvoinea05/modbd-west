import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayStationWestFormService } from './railway-station-west-form.service';
import { RailwayStationWestService } from '../service/railway-station-west.service';
import { IRailwayStationWest } from '../railway-station-west.model';

import { RailwayStationWestUpdateComponent } from './railway-station-west-update.component';

describe('RailwayStationWest Management Update Component', () => {
  let comp: RailwayStationWestUpdateComponent;
  let fixture: ComponentFixture<RailwayStationWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayStationWestFormService: RailwayStationWestFormService;
  let railwayStationWestService: RailwayStationWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayStationWestUpdateComponent],
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
      .overrideTemplate(RailwayStationWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayStationWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayStationWestFormService = TestBed.inject(RailwayStationWestFormService);
    railwayStationWestService = TestBed.inject(RailwayStationWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const railwayStationWest: IRailwayStationWest = { id: 456 };

      activatedRoute.data = of({ railwayStationWest });
      comp.ngOnInit();

      expect(comp.railwayStationWest).toEqual(railwayStationWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationWest>>();
      const railwayStationWest = { id: 123 };
      jest.spyOn(railwayStationWestFormService, 'getRailwayStationWest').mockReturnValue(railwayStationWest);
      jest.spyOn(railwayStationWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStationWest }));
      saveSubject.complete();

      // THEN
      expect(railwayStationWestFormService.getRailwayStationWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayStationWestService.update).toHaveBeenCalledWith(expect.objectContaining(railwayStationWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationWest>>();
      const railwayStationWest = { id: 123 };
      jest.spyOn(railwayStationWestFormService, 'getRailwayStationWest').mockReturnValue({ id: null });
      jest.spyOn(railwayStationWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStationWest }));
      saveSubject.complete();

      // THEN
      expect(railwayStationWestFormService.getRailwayStationWest).toHaveBeenCalled();
      expect(railwayStationWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStationWest>>();
      const railwayStationWest = { id: 123 };
      jest.spyOn(railwayStationWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStationWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayStationWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
