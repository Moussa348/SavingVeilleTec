import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './component/auth/auth.component';
import { ArtilceUtilityChartComponent } from './component/chart/artilce-utility-chart/artilce-utility-chart.component';
import { DailyExpenseDetailComponent } from './component/daily-expense-detail/daily-expense-detail.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { Error404Component } from './component/error404/error404.component';
import { HomeComponent } from './component/home/home.component';
import { NavComponent } from './component/nav/nav.component';
import { RegistrationVerifyCodeComponent } from './component/registration-verify-code/registration-verify-code.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { ResetPasswordVerificationComponent } from './component/reset-password-verification/reset-password-verification.component';
import { UserSettingsComponent } from './component/user-settings/user-settings.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { AuthGuardService } from './service/auth-guard.service';

const routes: Routes = [
  {path:'resetPasswordVerification/:code',component:ResetPasswordVerificationComponent},
  {path:'dailyExpenseDetail',component:DailyExpenseDetailComponent,canActivate:[AuthGuardService]},
  {path:'registrationVerifyCode/:code',component:RegistrationVerifyCodeComponent},
  {path:'userSettings',component:UserSettingsComponent,canActivate:[AuthGuardService]},
  {path:'dailyExpenseDetail',component:DailyExpenseDetailComponent,canActivate:[AuthGuardService]},
  {path:'registration',component:RegistrationComponent},
  {path:'nav',component:NavComponent},
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
