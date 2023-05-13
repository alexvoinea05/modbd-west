import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ticket-west.test-samples';

import { TicketWestFormService } from './ticket-west-form.service';

describe('TicketWest Form Service', () => {
  let service: TicketWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketWestFormService);
  });

  describe('Service methods', () => {
    describe('createTicketWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTicketWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finalPrice: expect.any(Object),
            quantity: expect.any(Object),
            time: expect.any(Object),
            appUserId: expect.any(Object),
            journeyId: expect.any(Object),
          })
        );
      });

      it('passing ITicketWest should create a new form with FormGroup', () => {
        const formGroup = service.createTicketWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finalPrice: expect.any(Object),
            quantity: expect.any(Object),
            time: expect.any(Object),
            appUserId: expect.any(Object),
            journeyId: expect.any(Object),
          })
        );
      });
    });

    describe('getTicketWest', () => {
      it('should return NewTicketWest for default TicketWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTicketWestFormGroup(sampleWithNewData);

        const ticketWest = service.getTicketWest(formGroup) as any;

        expect(ticketWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewTicketWest for empty TicketWest initial value', () => {
        const formGroup = service.createTicketWestFormGroup();

        const ticketWest = service.getTicketWest(formGroup) as any;

        expect(ticketWest).toMatchObject({});
      });

      it('should return ITicketWest', () => {
        const formGroup = service.createTicketWestFormGroup(sampleWithRequiredData);

        const ticketWest = service.getTicketWest(formGroup) as any;

        expect(ticketWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITicketWest should not enable id FormControl', () => {
        const formGroup = service.createTicketWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTicketWest should disable id FormControl', () => {
        const formGroup = service.createTicketWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
