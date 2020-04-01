import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnalysisComponent } from './analysis/analysis.component';
import { ConfigurationComponent } from './configuration/configuration.component';
import { FeedbackComponent } from './analysis/feedback/feedback.component';
import { ReportedErrorsComponent } from './analysis/reported-errors/reported-errors.component';
import { OfferReportsComponent } from './analysis/offer-reports/offer-reports.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'analysis', pathMatch: 'full' },
  { path: 'analysis', component: AnalysisComponent, children: [
    { path: '', redirectTo: 'analysis', pathMatch: 'full' },
    { path: 'errors', component: ReportedErrorsComponent },
    { path: 'reports', component: OfferReportsComponent },
    { path: 'feedback', component: FeedbackComponent },
  ] },
  { path: 'config', component: ConfigurationComponent },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
