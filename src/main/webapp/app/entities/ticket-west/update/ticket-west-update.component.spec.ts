import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TicketWestFormService } from './ticket-west-form.service';
import { TicketWestService } from '../service/ticket-west.service';
import { ITicketWest } from '../ticket-west.model';

import { TicketWestUpdateComponent } from './ticket-west-update.component';

describe('TicketWest Management Update Component', () => {
  let comp: TicketWestUpdateComponent;
  let fixture: ComponentFixture<TicketWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ticketWestFormService: TicketWestFormService;
  let ticketWestService: TicketWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TicketWestUpdateComponent],
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
      .overrideTemplate(TicketWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TicketWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ticketWestFormService = TestBed.inject(TicketWestFormService);
    ticketWestService = TestBed.inject(TicketWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const ticketWest: ITicketWest = { id: 456 };

      activatedRoute.data = of({ ticketWest });
      comp.ngOnInit();

      expect(comp.ticketWest).toEqual(ticketWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketWest>>();
      const ticketWest = { id: 123 };
      jest.spyOn(ticketWestFormService, 'getTicketWest').mockReturnValue(ticketWest);
      jest.spyOn(ticketWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketWest }));
      saveSubject.complete();

      // THEN
      expect(ticketWestFormService.getTicketWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ticketWestService.update).toHaveBeenCalledWith(expect.objectContaining(ticketWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketWest>>();
      const ticketWest = { id: 123 };
      jest.spyOn(ticketWestFormService, 'getTicketWest').mockReturnValue({ id: null });
      jest.spyOn(ticketWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ticketWest }));
      saveSubject.complete();

      // THEN
      expect(ticketWestFormService.getTicketWest).toHaveBeenCalled();
      expect(ticketWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITicketWest>>();
      const ticketWest = { id: 123 };
      jest.spyOn(ticketWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ticketWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ticketWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
