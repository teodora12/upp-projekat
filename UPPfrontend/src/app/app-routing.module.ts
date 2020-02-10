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
import {ReviewersPageComponent} from './component/reviewers-page/reviewers-page.component';
import {ChooseMagazinePageComponent} from './component/choose-magazine-page/choose-magazine-page.component';
import {WorkDataPageComponent} from './component/work-data-page/work-data-page.component';
import {PaymentPageComponent} from './component/payment-page/payment-page.component';
import {CoauthorsDataPageComponent} from './component/coauthors-data-page/coauthors-data-page.component';
import {WorksPageComponent} from './component/works-page/works-page.component';
import {CheckPdfPageComponent} from "./component/check-pdf-page/check-pdf-page.component";
import {WriteCommentPageComponent} from "./component/write-comment-page/write-comment-page.component";


const appRoutes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomePageComponent },
    { path: 'register', component: RegistrationPageComponent},
    { path: 'login', component: LoginPageComponent},
    { path: 'journal/new', component: JournalNewComponent},
    { path: 'scientificFields/:numOfScientificFields/:processInstanceId', component: ScientificFieldsPageComponent},
    { path: 'activate/:email/:processInstanceId', component: ActivationPageComponent},
    { path: 'reviewers', component: ReviewersPageComponent},
    { path: 'chooseMagazines', component: ChooseMagazinePageComponent},
    { path: 'workData/:processInstanceId/:magazineTitle', component: WorkDataPageComponent},
    { path: 'payment/:processInstanceId/:magazineTitle', component: PaymentPageComponent},
    { path: 'coauthorsData/:numOfCoauthors/:processInstanceId', component: CoauthorsDataPageComponent},
    { path: 'works', component: WorksPageComponent},
    { path: 'checkPdf', component: CheckPdfPageComponent },
    { path: 'mainRedactorComment', component: WriteCommentPageComponent}

];

@NgModule({
    declarations: [],
    imports: [RouterModule.forRoot(appRoutes), FormsModule, CommonModule],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
