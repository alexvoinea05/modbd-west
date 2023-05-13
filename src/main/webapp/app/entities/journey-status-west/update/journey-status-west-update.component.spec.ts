import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyStatusWestFormService } from './journey-status-west-form.service';
import { JourneyStatusWestService } from '../service/journey-status-west.service';
import { IJourneyStatusWest } from '../journey-status-west.model';

import { JourneyStatusWestUpdateComponent } from './journey-status-west-update.component';

describe('JourneyStatusWest Management Update Component', () => {
  let comp: JourneyStatusWestUpdateComponent;
  let fixture: ComponentFixture<JourneyStatusWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyStatusWestFormService: JourneyStatusWestFormService;
  let journeyStatusWestService: JourneyStatusWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyStatusWestUpdateComponent],
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
      .overrideTemplate(JourneyStatusWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyStatusWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyStatusWestFormService = TestBed.inject(JourneyStatusWestFormService);
    journeyStatusWestService = TestBed.inject(JourneyStatusWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const journeyStatusWest: IJourneyStatusWest = { id: 456 };

      activatedRoute.data = of({ journeyStatusWest });
      comp.ngOnInit();

      expect(comp.journeyStatusWest).toEqual(journeyStatusWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusWest>>();
      const journeyStatusWest = { id: 123 };
      jest.spyOn(journeyStatusWestFormService, 'getJourneyStatusWest').mockReturnValue(journeyStatusWest);
      jest.spyOn(journeyStatusWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatusWest }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusWestFormService.getJourneyStatusWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyStatusWestService.update).toHaveBeenCalledWith(expect.objectContaining(journeyStatusWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusWest>>();
      const journeyStatusWest = { id: 123 };
      jest.spyOn(journeyStatusWestFormService, 'getJourneyStatusWest').mockReturnValue({ id: null });
      jest.spyOn(journeyStatusWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatusWest }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusWestFormService.getJourneyStatusWest).toHaveBeenCalled();
      expect(journeyStatusWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatusWest>>();
      const journeyStatusWest = { id: 123 };
      jest.spyOn(journeyStatusWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatusWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyStatusWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
