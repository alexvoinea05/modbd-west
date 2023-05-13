import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainTypeWestFormService } from './train-type-west-form.service';
import { TrainTypeWestService } from '../service/train-type-west.service';
import { ITrainTypeWest } from '../train-type-west.model';

import { TrainTypeWestUpdateComponent } from './train-type-west-update.component';

describe('TrainTypeWest Management Update Component', () => {
  let comp: TrainTypeWestUpdateComponent;
  let fixture: ComponentFixture<TrainTypeWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainTypeWestFormService: TrainTypeWestFormService;
  let trainTypeWestService: TrainTypeWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainTypeWestUpdateComponent],
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
      .overrideTemplate(TrainTypeWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainTypeWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainTypeWestFormService = TestBed.inject(TrainTypeWestFormService);
    trainTypeWestService = TestBed.inject(TrainTypeWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainTypeWest: ITrainTypeWest = { id: 456 };

      activatedRoute.data = of({ trainTypeWest });
      comp.ngOnInit();

      expect(comp.trainTypeWest).toEqual(trainTypeWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeWest>>();
      const trainTypeWest = { id: 123 };
      jest.spyOn(trainTypeWestFormService, 'getTrainTypeWest').mockReturnValue(trainTypeWest);
      jest.spyOn(trainTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainTypeWest }));
      saveSubject.complete();

      // THEN
      expect(trainTypeWestFormService.getTrainTypeWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainTypeWestService.update).toHaveBeenCalledWith(expect.objectContaining(trainTypeWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeWest>>();
      const trainTypeWest = { id: 123 };
      jest.spyOn(trainTypeWestFormService, 'getTrainTypeWest').mockReturnValue({ id: null });
      jest.spyOn(trainTypeWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainTypeWest }));
      saveSubject.complete();

      // THEN
      expect(trainTypeWestFormService.getTrainTypeWest).toHaveBeenCalled();
      expect(trainTypeWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainTypeWest>>();
      const trainTypeWest = { id: 123 };
      jest.spyOn(trainTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainTypeWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
