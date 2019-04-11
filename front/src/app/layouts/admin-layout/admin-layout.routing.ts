import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { AccountComponent } from '../../modules/ror/account/list/account.component';
import { AccountsingleComponent } from '../../modules/ror/account/accountsingle/accountsingle.component';
import { ClientsListComponent } from '../../modules/ror/clients/clients-list/clients-list.component';
import { ClientsAddComponent } from '../../modules/ror/clients/clients-add/clients-add.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'user-profile', component: UserProfileComponent },
    { path: 'accounts', component: AccountComponent },
    { path: 'accounts/:id', component: AccountsingleComponent },
    { path: 'clients', component: ClientsListComponent },
    { path: 'client', component: ClientsAddComponent },
];
