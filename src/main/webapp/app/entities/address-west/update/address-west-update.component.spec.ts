import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AddressWestFormService } from './address-west-form.service';
import { AddressWestService } from '../service/address-west.service';
import { IAddressWest } from '../address-west.model';

import { AddressWestUpdateComponent } from './address-west-update.component';

describe('AddressWest Management Update Component', () => {
  let comp: AddressWestUpdateComponent;
  let fixture: ComponentFixture<AddressWestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressWestFormService: AddressWestFormService;
  let addressWestService: AddressWestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AddressWestUpdateComponent],
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
      .overrideTemplate(AddressWestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressWestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressWestFormService = TestBed.inject(AddressWestFormService);
    addressWestService = TestBed.inject(AddressWestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const addressWest: IAddressWest = { id: 456 };

      activatedRoute.data = of({ addressWest });
      comp.ngOnInit();

      expect(comp.addressWest).toEqual(addressWest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressWest>>();
      const addressWest = { id: 123 };
      jest.spyOn(addressWestFormService, 'getAddressWest').mockReturnValue(addressWest);
      jest.spyOn(addressWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addressWest }));
      saveSubject.complete();

      // THEN
      expect(addressWestFormService.getAddressWest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressWestService.update).toHaveBeenCalledWith(expect.objectContaining(addressWest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressWest>>();
      const addressWest = { id: 123 };
      jest.spyOn(addressWestFormService, 'getAddressWest').mockReturnValue({ id: null });
      jest.spyOn(addressWestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressWest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addressWest }));
      saveSubject.complete();

      // THEN
      expect(addressWestFormService.getAddressWest).toHaveBeenCalled();
      expect(addressWestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddressWest>>();
      const addressWest = { id: 123 };
      jest.spyOn(addressWestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addressWest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressWestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
