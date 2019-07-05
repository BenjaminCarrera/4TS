import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { AppSharedModule } from 'app/shared';
import { AppCoreModule } from 'app/core';
import { AppAppRoutingModule } from './app-routing.module';
import { AppHomeModule } from './home/home.module';
import { AppAccountModule } from './account/account.module';
import { AppEntityModule } from './entities/entity.module';
import * as moment from 'moment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import 'hammerjs';
import { AppAgregReqModule } from './agreg-req/agreg-req.module';
import { AppConReqModule } from './con-req/con-req.module';
import { AppResConreqModule } from './res-conreq/res-conreq.module';
import { AppAgregCandModule } from './agreg-cand/agreg-cand.module';
import { AppConCandModule } from './con-cand/con-cand.module';
import { AppResConcandModule } from './res-concand/res-concand.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
  imports: [
    BrowserModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-' }),
    NgJhipsterModule.forRoot({
      // set below to true to make alerts look like toast
      alertAsToast: false,
      alertTimeout: 5000,
      i18nEnabled: true,
      defaultI18nLang: 'es'
    }),
    AppSharedModule.forRoot(),
    AppCoreModule,
    AppHomeModule,
    AppAccountModule,
    AppAgregReqModule,
    AppConReqModule,
    AppResConreqModule,
    AppAgregCandModule,
    AppConCandModule,
    AppResConcandModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppEntityModule,
    AppAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: NotificationInterceptor,
      multi: true
    }
  ],
  bootstrap: [JhiMainComponent]
})
export class AppAppModule {
  constructor(private dpConfig: NgbDatepickerConfig) {
    this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
  }
}
