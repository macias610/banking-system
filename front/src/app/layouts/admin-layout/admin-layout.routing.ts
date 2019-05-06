import {Routes} from '@angular/router';

import {DashboardComponent} from '../../dashboard/dashboard.component';
import {AccountComponent} from '../../modules/ror/account/list/account.component';
import {AccountsingleComponent} from '../../modules/ror/account/accountsingle/accountsingle.component';
import {ClientsListComponent} from '../../modules/ror/clients/clients-list/clients-list.component';
import {ClientsAddComponent} from '../../modules/ror/clients/clients-add/clients-add.component';
import {ClientsEditComponent} from '../../modules/ror/clients/clients-edit/clients-edit.component';
import {TransferHistoryComponent} from '../../modules/moneytransfers/transfer-history/transfer-history.component';
import {TransferSendComponent} from '../../modules/moneytransfers/transfer-send/transfer-send.component';
import {TransferDetailComponent} from '../../modules/moneytransfers/transfer-detail/transfer-detail.component';
import {AccountAddComponent} from '../../modules/ror/account/account-add/account-add.component';
import {DirectDebitAgreementComponent} from '../../modules/moneytransfers/direct-debit-agreement/direct-debit-agreement.component';
import {DirectDebitListComponent} from '../../modules/moneytransfers/direct-debit-list/direct-debit-list.component';
import {PermanentTransferAddComponent} from '../../modules/moneytransfers/permanent-transfer-add/permanent-transfer-add.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'accounts', component: AccountComponent },
    { path: 'account', component: AccountAddComponent },
    { path: 'accounts/:id', component: AccountsingleComponent },
    { path: 'clients', component: ClientsListComponent },
    { path: 'client', component: ClientsAddComponent },
    { path: 'client/:id', component: ClientsEditComponent },
    { path: 'transferhistory', component: TransferHistoryComponent},
    { path: 'transfersend', component: TransferSendComponent},
    { path: 'permanentTransferAdd', component: PermanentTransferAddComponent},
    { path: 'transfers/:id', component: TransferDetailComponent },
    { path: 'directdebitagreement', component: DirectDebitAgreementComponent },
    { path: 'directdebitlist', component: DirectDebitListComponent},
];
