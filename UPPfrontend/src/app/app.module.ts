import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
// import {ToastrModule} from 'ngx-toastr';
import { AppComponent } from './app.component';
import { JournalNewComponent } from './component/journal-new/journal-new.component';
import { HomePageComponent } from './component/home-page/home-page.component';
import { RegistrationPageComponent } from './component/registration-page/registration-page.component';
import { LoginPageComponent } from './component/login-page/login-page.component';
import { NavbarComponent } from './component/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    JournalNewComponent,
    HomePageComponent,
    RegistrationPageComponent,
    LoginPageComponent,
    NavbarComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
