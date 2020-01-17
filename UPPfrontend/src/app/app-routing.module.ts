import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HomePageComponent} from './component/home-page/home-page.component';
import {RegistrationPageComponent} from './component/registration-page/registration-page.component';
import {LoginPageComponent} from './component/login-page/login-page.component';
import {JournalNewComponent} from './component/journal-new/journal-new.component';
import {ScientificFieldsPageComponent} from './component/scientific-fields-page/scientific-fields-page.component';
import {ActivationPageComponent} from './component/activation-page/activation-page.component';


const appRoutes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomePageComponent },
    { path: 'register', component: RegistrationPageComponent},
    { path: 'login', component: LoginPageComponent},
    { path: 'journal/new', component: JournalNewComponent},
    { path: 'scientificFields/:numOfScientificFields/:processInstanceId', component: ScientificFieldsPageComponent},
    { path: 'activate/:email/:processInstanceId', component: ActivationPageComponent}
];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes), FormsModule, CommonModule],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
