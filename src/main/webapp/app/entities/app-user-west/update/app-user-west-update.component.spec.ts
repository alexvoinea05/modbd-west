import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AppUserWestFormService } from './app-user-west-form.service';
import { AppUserWestService } from '../service/app-user-west.service';
import { IAppUserWest } from '../app-user-west.model';

import { AppUserWestUpdateComponent } from './app-user-west-update.component';

describe('AppUserWest Management Update Component', () => {
  let comp: AppUserWestUpdateComponent;
  let fixture: ComponentFixture<AppUserWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let appUserWestFormService: AppUserWestFormService;
  let appUserWestService: AppUserWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AppUserWestUpdateComponent],
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
      .overrideTemplate(AppUserWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppUserWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appUserWestFormService = TestBed.inject(AppUserWestFormService);
    appUserWestService = TestBed.inject(AppUserWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const appUserWest: IAppUserWest = { id: 456 };

      activatedRoute.data = of({ appUserWest });
      comp.ngOnInit();

      expect(comp.appUserWest).toEqual(appUserWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserWest>>();
      const appUserWest = { id: 123 };
      jest.spyOn(appUserWestFormService, 'getAppUserWest').mockReturnValue(appUserWest);
      jest.spyOn(appUserWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appUserWest }));
      saveSubject.complete();

      // THEN
      expect(appUserWestFormService.getAppUserWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appUserWestService.update).toHaveBeenCalledWith(expect.objectContaining(appUserWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserWest>>();
      const appUserWest = { id: 123 };
      jest.spyOn(appUserWestFormService, 'getAppUserWest').mockReturnValue({ id: null });
      jest.spyOn(appUserWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appUserWest }));
      saveSubject.complete();

      // THEN
      expect(appUserWestFormService.getAppUserWest).toHaveBeenCalled();
      expect(appUserWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppUserWest>>();
      const appUserWest = { id: 123 };
      jest.spyOn(appUserWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appUserWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appUserWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
