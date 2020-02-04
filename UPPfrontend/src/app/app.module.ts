import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
// import {ToastrModule} from 'ngx-toastr';
import { AppComponent } from './app.component';
import { JournalNewComponent } from './component/journal-new/journal-new.component';
import { HomePageComponent } from './component/home-page/home-page.component';
import { RegistrationPageComponent } from './component/registration-page/registration-page.component';
import { LoginPageComponent } from './component/login-page/login-page.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import {ToastrManager, ToastrModule} from 'ng6-toastr-notifications';
import {CanActivateService} from './service/security/can-activate.service';
import {TokenInterceptorService} from './service/security/token-interceptor';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { ScientificFieldsPageComponent } from './component/scientific-fields-page/scientific-fields-page.component';
import { ActivationPageComponent } from './component/activation-page/activation-page.component';
import { ReviewersPageComponent } from './component/reviewers-page/reviewers-page.component';
import { ChooseMagazinePageComponent } from './component/choose-magazine-page/choose-magazine-page.component';

@NgModule({
  declarations: [
    AppComponent,
    JournalNewComponent,
    HomePageComponent,
    RegistrationPageComponent,
    LoginPageComponent,
    NavbarComponent,
    ScientificFieldsPageComponent,
    ActivationPageComponent,
    ReviewersPageComponent,
    ChooseMagazinePageComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      ToastrModule.forRoot(),
      BrowserAnimationsModule
  ],
  providers: [
      CanActivateService,
      {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptorService,
          multi: true
      }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
