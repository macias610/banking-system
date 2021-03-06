import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { AccountComponent } from '../../modules/ror/account/list/account.component';
import { ChartsModule } from 'ng2-charts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';
import { AccountService } from '../../modules/ror/account/account.service';
import { AccountsingleComponent } from '../../modules/ror/account/accountsingle/accountsingle.component';
import { ClientsListComponent } from '../../modules/ror/clients/clients-list/clients-list.component';
import { ClientsAddComponent } from '../../modules/ror/clients/clients-add/clients-add.component';
import { ClientsEditComponent } from '../../modules/ror/clients/clients-edit/clients-edit.component';
import { AccountNumberPipe } from '../../shared/pipes/account-number.pipe';
import { CustomFilterPipe } from '../../shared/pipes/custom-filter.pipe';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(AdminLayoutRoutes),
        FormsModule,
        ReactiveFormsModule,
        ChartsModule,
        NgbModule,
        HttpClientModule,
        ToastrModule.forRoot()
    ],
    declarations: [
        DashboardComponent,
        NotificationsComponent,
        AccountComponent,
        AccountsingleComponent,
        ClientsListComponent,
        ClientsAddComponent,
        ClientsEditComponent,
        AccountNumberPipe,
        CustomFilterPipe
    ],
    providers: [
        AccountService
    ]
})

export class AdminLayoutModule { }
