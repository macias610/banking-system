import {Routes} from '@angular/router';

import {DashboardComponent} from '../../dashboard/dashboard.component';
import {AccountComponent} from '../../modules/ror/account/list/account.component';
import {AccountsingleComponent} from '../../modules/ror/account/accountsingle/accountsingle.component';
import {ClientsListComponent} from '../../modules/ror/clients/clients-list/clients-list.component';
import {ClientsAddComponent} from '../../modules/ror/clients/clients-add/clients-add.component';
import {ClientsEditComponent} from '../../modules/ror/clients/clients-edit/clients-edit.component';
import {TransferHistoryComponent} from '../../modules/moneytransfers/transfer-history/transfer-history.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'accounts', component: AccountComponent },
    { path: 'accounts/:id', component: AccountsingleComponent },
    { path: 'clients', component: ClientsListComponent },
    { path: 'client', component: ClientsAddComponent },
    { path: 'client/:id', component: ClientsEditComponent },
    { path: 'transferhistory', component: TransferHistoryComponent},
];
