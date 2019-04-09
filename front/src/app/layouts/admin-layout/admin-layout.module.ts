import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { AccountComponent } from '../../modules/ror/account/list/account.component';
import { ChartsModule } from 'ng2-charts';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';
import { AccountService } from '../../modules/ror/account/account.service';
import { AccountsingleComponent } from '../../modules/ror/account/accountsingle/accountsingle.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(AdminLayoutRoutes),
        FormsModule,
        ChartsModule,
        NgbModule,
        HttpClientModule,
        ToastrModule.forRoot()
    ],
    declarations: [
        DashboardComponent,
        UserProfileComponent,
        NotificationsComponent,
        AccountComponent,
        AccountsingleComponent
    ],
    providers: [
        AccountService
    ]
})

export class AdminLayoutModule { }
