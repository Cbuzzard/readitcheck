import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SubmissionComponent } from './submission/submission.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'submission/:id', component: SubmissionComponent },
  { path: 'user/:id', component: UserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }