import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { AccountComponent } from '../../modules/ror/account/list/account.component';
import { AccountsingleComponent } from '../../modules/ror/account/accountsingle/accountsingle.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'user-profile', component: UserProfileComponent },
    { path: 'accounts', component: AccountComponent },
    { path: 'accounts/:id', component: AccountsingleComponent },
    { path: 'notifications', component: NotificationsComponent },
];
