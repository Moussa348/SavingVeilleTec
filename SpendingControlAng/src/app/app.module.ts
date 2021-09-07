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

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    Error404Component,
    AmbientMusicComponent,
    HomeComponent
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
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
