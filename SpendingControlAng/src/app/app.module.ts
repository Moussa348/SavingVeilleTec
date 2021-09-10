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
import { DatePipe } from '@angular/common';

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
    ListArticleDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
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
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatMenuModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
