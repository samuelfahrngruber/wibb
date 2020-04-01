import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SideNavComponent } from './analysis/side-nav/side-nav.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { ConfigurationComponent } from './configuration/configuration.component';

import { AppRoutingModule } from './app-routing.module';
import { ReportedErrorsComponent } from './analysis/reported-errors/reported-errors.component';
import { OfferReportsComponent } from './analysis/offer-reports/offer-reports.component';
import { FeedbackComponent } from './analysis/feedback/feedback.component';
import { RequestListComponent } from './analysis/feedback/request-list/request-list.component';
import { HttpClientModule } from '@angular/common/http';
import { OfferItemComponent } from './offer-item/offer-item.component';
import { OfferReportItemComponent } from './analysis/offer-reports/offer-report-item/offer-report-item.component';
import { ErrorItemComponent } from './analysis/reported-errors/error-item/error-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SideNavComponent,
    AnalysisComponent,
    ConfigurationComponent,
    ReportedErrorsComponent,
    OfferReportsComponent,
    FeedbackComponent,
    RequestListComponent,
    OfferItemComponent,
    OfferReportItemComponent,
    ErrorItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
