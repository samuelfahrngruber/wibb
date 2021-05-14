import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { WibbApiService } from './wibb-api.service';
import { BeerComponent } from './beer/beer.component';
import { StoreComponent } from './store/store.component';
import { OfferComponent } from './offer/offer.component';
import { ApiResourcePipePipe } from './api-resource-pipe.pipe';
import { AddOfferComponent } from './add-offer/add-offer.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    BeerComponent,
    StoreComponent,
    OfferComponent,
    ApiResourcePipePipe,
    AddOfferComponent,
  ],
  imports: [BrowserModule, FormsModule],
  providers: [WibbApiService],
  bootstrap: [AppComponent],
})
export class AppModule {}
