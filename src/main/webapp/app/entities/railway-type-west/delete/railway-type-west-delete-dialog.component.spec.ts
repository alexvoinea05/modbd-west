jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { RailwayTypeWestService } from '../service/railway-type-west.service';

import { RailwayTypeWestDeleteDialogComponent } from './railway-type-west-delete-dialog.component';

describe('RailwayTypeWest Management Delete Component', () => {
  let comp: RailwayTypeWestDeleteDialogComponent;
  let fixture: ComponentFixture<RailwayTypeWestDeleteDialogComponent>;
  let service: RailwayTypeWestService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RailwayTypeWestDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(RailwayTypeWestDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayTypeWestDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RailwayTypeWestService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
