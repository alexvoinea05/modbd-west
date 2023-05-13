import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AddressWestDetailComponent } from './address-west-detail.component';

describe('AddressWest Management Detail Component', () => {
  let comp: AddressWestDetailComponent;
  let fixture: ComponentFixture<AddressWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddressWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ addressWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AddressWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddressWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load addressWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.addressWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
