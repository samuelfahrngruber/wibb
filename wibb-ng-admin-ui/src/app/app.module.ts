import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AnalysisComponent } from './analysis/analysis.component';
import { ConfigurationComponent } from './configuration/configuration.component';

import { AppRoutingModule } from './app-routing.module';
import { ReportedErrorsComponent } from './analysis/reported-errors/reported-errors.component';
import { OfferReportsComponent } from './analysis/offer-reports/offer-reports.component';
import { FeedbackComponent } from './analysis/feedback/feedback.component';
import { RequestListComponent } from './analysis/feedback/request-list/request-list.component';
import { HttpClientModule } from '@angular/common/http';
import { OfferItemComponent } from './offer-item/offer-item.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatChipsModule } from '@angular/material/chips';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AnalysisComponent,
    ConfigurationComponent,
    ReportedErrorsComponent,
    OfferReportsComponent,
    FeedbackComponent,
    RequestListComponent,
    OfferItemComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatExpansionModule,
    MatChipsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
