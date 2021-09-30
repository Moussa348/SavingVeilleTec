import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { JwtModule } from '@auth0/angular-jwt';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ALLOWED_URLS,STORAGE_KEY } from './util/constant';
import {MatGridListModule} from '@angular/material/grid-list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { Error404Component } from './component/error404/error404.component';
import {MatSliderModule} from '@angular/material/slider';
import {MatStepperModule} from '@angular/material/stepper';
import { AmbientMusicComponent } from './component/ambient-music/ambient-music.component';
import { HomeComponent } from './component/home/home.component';
import { NavComponent } from './component/nav/nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { FooterComponent } from './component/footer/footer.component';
import { AuthComponent } from './component/auth/auth.component';
import { ArtilceUtilityChartComponent } from './component/chart/artilce-utility-chart/artilce-utility-chart.component';
import { ListArticleDetailComponent } from './component/list-article-detail/list-article-detail.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { DatePipe } from '@angular/common';
import { RegistrationComponent } from './component/registration/registration.component';
import { RegistrationVerifyCodeComponent } from './component/registration-verify-code/registration-verify-code.component';
import { AuthGuardService } from './service/auth-guard.service';
import { AddingArticleComponent } from './component/adding-article/adding-article.component';
import { DailyExpenseDetailComponent } from './component/daily-expense-detail/daily-expense-detail.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatTabsModule} from '@angular/material/tabs';
import { ArticlePriceChartComponent } from './component/chart/article-price-chart/article-price-chart.component';
import { ListArticleAnalyticComponent } from './component/list-article-analytic/list-article-analytic.component';
import { UserSettingsComponent } from './component/user-settings/user-settings.component';
import { DisableAccountDialogComponent } from './component/disable-account-dialog/disable-account-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    Error404Component,
    AmbientMusicComponent,
    HomeComponent,
    NavComponent,
    DashboardComponent,
    FooterComponent,
    AuthComponent,
    ArtilceUtilityChartComponent,
    ListArticleDetailComponent,
    RegistrationComponent,
    RegistrationVerifyCodeComponent,
    AddingArticleComponent,
    DailyExpenseDetailComponent,
    ArticlePriceChartComponent,
    ListArticleAnalyticComponent,
    UserSettingsComponent,
    DisableAccountDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatStepperModule,
    MatTabsModule,
    MatSliderModule,
    JwtModule.forRoot({
      config:{
        tokenGetter: () => {
          return sessionStorage.getItem(STORAGE_KEY);
        },
        allowedDomains:ALLOWED_URLS
      },
    }),
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatSlideToggleModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatMenuModule,
    NgbModule
  ],
  providers: [DatePipe,AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
