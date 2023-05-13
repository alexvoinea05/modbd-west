import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppUserWestDetailComponent } from './app-user-west-detail.component';

describe('AppUserWest Management Detail Component', () => {
  let comp: AppUserWestDetailComponent;
  let fixture: ComponentFixture<AppUserWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppUserWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ appUserWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AppUserWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AppUserWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load appUserWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.appUserWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
