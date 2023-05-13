import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UserTypeWestFormService } from './user-type-west-form.service';
import { UserTypeWestService } from '../service/user-type-west.service';
import { IUserTypeWest } from '../user-type-west.model';

import { UserTypeWestUpdateComponent } from './user-type-west-update.component';

describe('UserTypeWest Management Update Component', () => {
  let comp: UserTypeWestUpdateComponent;
  let fixture: ComponentFixture<UserTypeWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let userTypeWestFormService: UserTypeWestFormService;
  let userTypeWestService: UserTypeWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UserTypeWestUpdateComponent],
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
      .overrideTemplate(UserTypeWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserTypeWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    userTypeWestFormService = TestBed.inject(UserTypeWestFormService);
    userTypeWestService = TestBed.inject(UserTypeWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const userTypeWest: IUserTypeWest = { id: 456 };

      activatedRoute.data = of({ userTypeWest });
      comp.ngOnInit();

      expect(comp.userTypeWest).toEqual(userTypeWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeWest>>();
      const userTypeWest = { id: 123 };
      jest.spyOn(userTypeWestFormService, 'getUserTypeWest').mockReturnValue(userTypeWest);
      jest.spyOn(userTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userTypeWest }));
      saveSubject.complete();

      // THEN
      expect(userTypeWestFormService.getUserTypeWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(userTypeWestService.update).toHaveBeenCalledWith(expect.objectContaining(userTypeWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeWest>>();
      const userTypeWest = { id: 123 };
      jest.spyOn(userTypeWestFormService, 'getUserTypeWest').mockReturnValue({ id: null });
      jest.spyOn(userTypeWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: userTypeWest }));
      saveSubject.complete();

      // THEN
      expect(userTypeWestFormService.getUserTypeWest).toHaveBeenCalled();
      expect(userTypeWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUserTypeWest>>();
      const userTypeWest = { id: 123 };
      jest.spyOn(userTypeWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ userTypeWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(userTypeWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
