import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyWestFormService } from './journey-west-form.service';
import { JourneyWestService } from '../service/journey-west.service';
import { IJourneyWest } from '../journey-west.model';

import { JourneyWestUpdateComponent } from './journey-west-update.component';

describe('JourneyWest Management Update Component', () => {
  let comp: JourneyWestUpdateComponent;
  let fixture: ComponentFixture<JourneyWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyWestFormService: JourneyWestFormService;
  let journeyWestService: JourneyWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyWestUpdateComponent],
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
      .overrideTemplate(JourneyWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyWestFormService = TestBed.inject(JourneyWestFormService);
    journeyWestService = TestBed.inject(JourneyWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const journeyWest: IJourneyWest = { id: 456 };

      activatedRoute.data = of({ journeyWest });
      comp.ngOnInit();

      expect(comp.journeyWest).toEqual(journeyWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyWest>>();
      const journeyWest = { id: 123 };
      jest.spyOn(journeyWestFormService, 'getJourneyWest').mockReturnValue(journeyWest);
      jest.spyOn(journeyWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyWest }));
      saveSubject.complete();

      // THEN
      expect(journeyWestFormService.getJourneyWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyWestService.update).toHaveBeenCalledWith(expect.objectContaining(journeyWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyWest>>();
      const journeyWest = { id: 123 };
      jest.spyOn(journeyWestFormService, 'getJourneyWest').mockReturnValue({ id: null });
      jest.spyOn(journeyWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyWest }));
      saveSubject.complete();

      // THEN
      expect(journeyWestFormService.getJourneyWest).toHaveBeenCalled();
      expect(journeyWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyWest>>();
      const journeyWest = { id: 123 };
      jest.spyOn(journeyWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
