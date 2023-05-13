import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainWestFormService } from './train-west-form.service';
import { TrainWestService } from '../service/train-west.service';
import { ITrainWest } from '../train-west.model';

import { TrainWestUpdateComponent } from './train-west-update.component';

describe('TrainWest Management Update Component', () => {
  let comp: TrainWestUpdateComponent;
  let fixture: ComponentFixture<TrainWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainWestFormService: TrainWestFormService;
  let trainWestService: TrainWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainWestUpdateComponent],
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
      .overrideTemplate(TrainWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainWestFormService = TestBed.inject(TrainWestFormService);
    trainWestService = TestBed.inject(TrainWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainWest: ITrainWest = { id: 456 };

      activatedRoute.data = of({ trainWest });
      comp.ngOnInit();

      expect(comp.trainWest).toEqual(trainWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainWest>>();
      const trainWest = { id: 123 };
      jest.spyOn(trainWestFormService, 'getTrainWest').mockReturnValue(trainWest);
      jest.spyOn(trainWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainWest }));
      saveSubject.complete();

      // THEN
      expect(trainWestFormService.getTrainWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainWestService.update).toHaveBeenCalledWith(expect.objectContaining(trainWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainWest>>();
      const trainWest = { id: 123 };
      jest.spyOn(trainWestFormService, 'getTrainWest').mockReturnValue({ id: null });
      jest.spyOn(trainWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainWest }));
      saveSubject.complete();

      // THEN
      expect(trainWestFormService.getTrainWest).toHaveBeenCalled();
      expect(trainWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainWest>>();
      const trainWest = { id: 123 };
      jest.spyOn(trainWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
