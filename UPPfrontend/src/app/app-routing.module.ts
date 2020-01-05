import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';


const appRoutes: Routes = [


];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes), FormsModule],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
