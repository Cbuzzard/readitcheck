import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginButtonComponent } from './login-button/login-button.component';
import { HttpInterceptorService } from './service/interceptor/http-interceptor.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { HeaderComponent } from './header/header.component';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import { NewSubmissionComponent } from './new-submission/new-submission.component';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatExpansionModule} from '@angular/material/expansion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { SubmissionFormDialogComponent } from './submission-form-dialog/submission-form-dialog.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { SubmissionComponent } from './submission/submission.component';
import { SimpleSubmissionComponent } from './simple-submission/simple-submission.component';
import {MatDividerModule} from '@angular/material/divider';
import { UserComponent } from './user/user.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTooltipModule} from '@angular/material/tooltip';
import { QuestionComponent } from './question/question.component';
import { CommentsComponent } from './comments/comments.component';
import { NewCommentComponent } from './new-comment/new-comment.component';


//TODO organize imports

@NgModule({
  declarations: [
    AppComponent,
    LoginButtonComponent,
    HeaderComponent,
    NewSubmissionComponent,
    SubmissionFormDialogComponent,
    HomeComponent,
    SubmissionComponent,
    SimpleSubmissionComponent,
    UserComponent,
    QuestionComponent,
    CommentsComponent,
    NewCommentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    AppRoutingModule,
    MatDividerModule,
    InfiniteScrollModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
