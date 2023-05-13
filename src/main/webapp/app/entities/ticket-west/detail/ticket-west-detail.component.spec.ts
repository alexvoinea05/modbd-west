import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TicketWestDetailComponent } from './ticket-west-detail.component';

describe('TicketWest Management Detail Component', () => {
  let comp: TicketWestDetailComponent;
  let fixture: ComponentFixture<TicketWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TicketWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ticketWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TicketWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TicketWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ticketWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ticketWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
