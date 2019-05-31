import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { AccountComponent } from '../../modules/ror/account/list/account.component';
import { AccountsingleComponent } from '../../modules/ror/account/accountsingle/accountsingle.component';
import { ClientsListComponent } from '../../modules/ror/clients/clients-list/clients-list.component';
import { ClientsAddComponent } from '../../modules/ror/clients/clients-add/clients-add.component';
import { ClientsEditComponent } from '../../modules/ror/clients/clients-edit/clients-edit.component';
import { TransferHistoryComponent } from '../../modules/moneytransfers/transfer-history/transfer-history.component';
import { TransferSendComponent } from '../../modules/moneytransfers/transfer-send/transfer-send.component';
import { TransferDetailComponent } from '../../modules/moneytransfers/transfer-detail/transfer-detail.component';
import { AccountAddComponent } from '../../modules/ror/account/account-add/account-add.component';
import { DirectDebitAgreementComponent } from '../../modules/moneytransfers/direct-debit-agreement/direct-debit-agreement.component';
import { DirectDebitListComponent } from '../../modules/moneytransfers/direct-debit-list/direct-debit-list.component';
import { PermanentTransferAddComponent } from '../../modules/moneytransfers/permanent-transfer-add/permanent-transfer-add.component';
import { DirectDebitSendComponent } from '../../modules/moneytransfers/direct-debit-send/direct-debit-send.component';
import { PermanentTransferListComponent } from '../../modules/moneytransfers/permanent-transfer-list/permanent-transfer-list.component';
import { DepositsAddComponent } from "../../modules/deposits/deposits-add/deposits-add.component";
import { DepositsListComponent } from "../../modules/deposits/deposits-list/deposits-list.component";
import { DepositTypesListComponent } from '../../modules/deposits/deposit-types-list/deposit-types-list.component';
import { DepositTypesAddComponent } from '../../modules/deposits/deposit-types-add/deposit-types-add.component';
import { CreditTypesListComponent } from '../../modules/credits/credit-types-list/credit-types-list.component';
import { CreditTypesAddComponent } from '../../modules/credits/credit-types-add/credit-types-add.component';
import { CreditsListComponent } from '../../modules/credits/credits-list/credits-list.component';
import { CreditsAddComponent } from '../../modules/credits/credits-add/credits-add.component';
import { PaymentSchedulesListComponent } from '../../modules/credits/payment-schedules-list/payment-schedules-list.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'accounts', component: AccountComponent },
    { path: 'account', component: AccountAddComponent },
    { path: 'accounts/:id', component: AccountsingleComponent },
    { path: 'clients', component: ClientsListComponent },
    { path: 'client', component: ClientsAddComponent },
    { path: 'client/:id', component: ClientsEditComponent },
    { path: 'transfersend/:id', component: TransferSendComponent },
    { path: 'permanentTransferAdd', component: PermanentTransferAddComponent },
    { path: 'permanentTransferList', component: PermanentTransferListComponent },
    { path: 'transfers/:id', component: TransferDetailComponent },
    { path: 'directdebitagreement', component: DirectDebitAgreementComponent },
    { path: 'directdebitlist', component: DirectDebitListComponent },
    { path: 'directdebitsend', component: DirectDebitSendComponent },
    { path: 'deposits/add/:id', component: DepositsAddComponent },
    { path: 'deposits/:id', component: DepositsListComponent },
    { path: 'deposit-types', component: DepositTypesListComponent },
    { path: 'deposit-types/add', component: DepositTypesAddComponent },
    { path: 'credit-types', component: CreditTypesListComponent},
    { path: 'credit-types/add', component: CreditTypesAddComponent},
    { path: 'credit/:id', component: CreditsListComponent},
    { path: 'credit/add/:id', component: CreditsAddComponent},
    { path: 'payment-schedule/:id', component: PaymentSchedulesListComponent},
];
