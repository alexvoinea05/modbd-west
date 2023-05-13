import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IJourneyStatusWest } from '../journey-status-west.model';
import { JourneyStatusWestService } from '../service/journey-status-west.service';

import { JourneyStatusWestRoutingResolveService } from './journey-status-west-routing-resolve.service';

describe('JourneyStatusWest routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: JourneyStatusWestRoutingResolveService;
  let service: JourneyStatusWestService;
  let resultJourneyStatusWest: IJourneyStatusWest | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(JourneyStatusWestRoutingResolveService);
    service = TestBed.inject(JourneyStatusWestService);
    resultJourneyStatusWest = undefined;
  });

  describe('resolve', () => {
    it('should return IJourneyStatusWest returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusWest = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJourneyStatusWest).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusWest = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultJourneyStatusWest).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IJourneyStatusWest>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultJourneyStatusWest = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultJourneyStatusWest).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
