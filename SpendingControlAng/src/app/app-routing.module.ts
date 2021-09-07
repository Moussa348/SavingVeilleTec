import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Error404Component } from './component/error404/error404.component';
import { WelcomeComponent } from './component/welcome/welcome.component';

const routes: Routes = [
  {path:'welcome',component:WelcomeComponent},
  {path:'',redirectTo: 'welcome', pathMatch: 'full'},
  {path:'**', component:Error404Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
