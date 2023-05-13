import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserTypeWestDetailComponent } from './user-type-west-detail.component';

describe('UserTypeWest Management Detail Component', () => {
  let comp: UserTypeWestDetailComponent;
  let fixture: ComponentFixture<UserTypeWestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserTypeWestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userTypeWest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserTypeWestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserTypeWestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userTypeWest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userTypeWest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
