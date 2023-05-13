import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address-west',
        data: { pageTitle: 'AddressWests' },
        loadChildren: () => import('./address-west/address-west.module').then(m => m.AddressWestModule),
      },
      {
        path: 'app-user-west',
        data: { pageTitle: 'AppUserWests' },
        loadChildren: () => import('./app-user-west/app-user-west.module').then(m => m.AppUserWestModule),
      },
      {
        path: 'city-west',
        data: { pageTitle: 'CityWests' },
        loadChildren: () => import('./city-west/city-west.module').then(m => m.CityWestModule),
      },
      {
        path: 'company-west',
        data: { pageTitle: 'CompanyWests' },
        loadChildren: () => import('./company-west/company-west.module').then(m => m.CompanyWestModule),
      },
      {
        path: 'company-license-west',
        data: { pageTitle: 'CompanyLicenseWests' },
        loadChildren: () => import('./company-license-west/company-license-west.module').then(m => m.CompanyLicenseWestModule),
      },
      {
        path: 'district-west',
        data: { pageTitle: 'DistrictWests' },
        loadChildren: () => import('./district-west/district-west.module').then(m => m.DistrictWestModule),
      },
      {
        path: 'fuel-type-west',
        data: { pageTitle: 'FuelTypeWests' },
        loadChildren: () => import('./fuel-type-west/fuel-type-west.module').then(m => m.FuelTypeWestModule),
      },
      {
        path: 'journey-west',
        data: { pageTitle: 'JourneyWests' },
        loadChildren: () => import('./journey-west/journey-west.module').then(m => m.JourneyWestModule),
      },
      {
        path: 'journey-status-west',
        data: { pageTitle: 'JourneyStatusWests' },
        loadChildren: () => import('./journey-status-west/journey-status-west.module').then(m => m.JourneyStatusWestModule),
      },
      {
        path: 'license-west',
        data: { pageTitle: 'LicenseWests' },
        loadChildren: () => import('./license-west/license-west.module').then(m => m.LicenseWestModule),
      },
      {
        path: 'railway-station-west',
        data: { pageTitle: 'RailwayStationWests' },
        loadChildren: () => import('./railway-station-west/railway-station-west.module').then(m => m.RailwayStationWestModule),
      },
      {
        path: 'railway-type-west',
        data: { pageTitle: 'RailwayTypeWests' },
        loadChildren: () => import('./railway-type-west/railway-type-west.module').then(m => m.RailwayTypeWestModule),
      },
      {
        path: 'ticket-west',
        data: { pageTitle: 'TicketWests' },
        loadChildren: () => import('./ticket-west/ticket-west.module').then(m => m.TicketWestModule),
      },
      {
        path: 'train-west',
        data: { pageTitle: 'TrainWests' },
        loadChildren: () => import('./train-west/train-west.module').then(m => m.TrainWestModule),
      },
      {
        path: 'train-type-west',
        data: { pageTitle: 'TrainTypeWests' },
        loadChildren: () => import('./train-type-west/train-type-west.module').then(m => m.TrainTypeWestModule),
      },
      {
        path: 'user-type-west',
        data: { pageTitle: 'UserTypeWests' },
        loadChildren: () => import('./user-type-west/user-type-west.module').then(m => m.UserTypeWestModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
