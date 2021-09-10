import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './component/auth/auth.component';
import { ArtilceUtilityChartComponent } from './component/chart/artilce-utility-chart/artilce-utility-chart.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { Error404Component } from './component/error404/error404.component';
import { HomeComponent } from './component/home/home.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { AuthGuardService } from './service/auth-guard.service';

const routes: Routes = [
  {path:'authentication',component:AuthComponent},
  {path:'dashboard',component:DashboardComponent,canActivate:[AuthGuardService]},
  {path:'home',component:HomeComponent},
  {path:'welcome',component:WelcomeComponent},
  {path:'',redirectTo: 'welcome', pathMatch: 'full'},
  {path:'**', component:Error404Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
