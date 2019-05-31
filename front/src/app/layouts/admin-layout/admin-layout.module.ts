import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AdminLayoutRoutes} from './admin-layout.routing';
import {DashboardComponent} from '../../dashboard/dashboard.component';
import {NotificationsComponent} from '../../notifications/notifications.component';
import {AccountComponent} from '../../modules/ror/account/list/account.component';
import {ChartsModule} from 'ng2-charts';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastrModule} from 'ngx-toastr';
import {AccountService} from '../../modules/ror/account/account.service';
import {AccountsingleComponent} from '../../modules/ror/account/accountsingle/accountsingle.component';
import {ClientsListComponent} from '../../modules/ror/clients/clients-list/clients-list.component';
import {ClientsAddComponent} from '../../modules/ror/clients/clients-add/clients-add.component';
import {ClientsEditComponent} from '../../modules/ror/clients/clients-edit/clients-edit.component';
import {AccountNumberPipe} from '../../shared/pipes/account-number.pipe';
import {CustomFilterPipe} from '../../shared/pipes/custom-filter.pipe';
import {AccountAddComponent} from '../../modules/ror/account/account-add/account-add.component';
import {TransferDetailComponent} from '../../modules/moneytransfers/transfer-detail/transfer-detail.component';
import {IbanNumberPipe} from '../../shared/pipes/iban-number.pipe';
import {TransferSendComponent} from '../../modules/moneytransfers/transfer-send/transfer-send.component';
import {TransferHistoryComponent} from '../../modules/moneytransfers/transfer-history/transfer-history.component';
import {CardService} from '../../modules/ror/account/card.service';
import {NotificationService} from '../../shared/services/notification.service';
import {DirectDebitAgreementComponent} from '../../modules/moneytransfers/direct-debit-agreement/direct-debit-agreement.component';
import {DirectDebitListComponent} from '../../modules/moneytransfers/direct-debit-list/direct-debit-list.component';
import {PermanentTransferAddComponent} from '../../modules/moneytransfers/permanent-transfer-add/permanent-transfer-add.component';
import {DirectDebitSendComponent} from '../../modules/moneytransfers/direct-debit-send/direct-debit-send.component';
import {PermanentTransferListComponent} from '../../modules/moneytransfers/permanent-transfer-list/permanent-transfer-list.component';
import {DepositsAddComponent} from "../../modules/deposits/deposits-add/deposits-add.component";
import {DepositsListComponent} from "../../modules/deposits/deposits-list/deposits-list.component";
import {DepositTypesListComponent} from '../../modules/deposits/deposit-types-list/deposit-types-list.component';
import {DepositTypesAddComponent} from '../../modules/deposits/deposit-types-add/deposit-types-add.component';
import { CreditsListComponent } from '../../modules/credits/credits-list/credits-list.component';
import { CreditTypesListComponent } from '../../modules/credits/credit-types-list/credit-types-list.component';
import { CreditTypesAddComponent } from '../../modules/credits/credit-types-add/credit-types-add.component';
import { CreditsAddComponent } from '../../modules/credits/credits-add/credits-add.component';

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
        AccountAddComponent,
        AccountsingleComponent,
        ClientsListComponent,
        ClientsAddComponent,
        ClientsEditComponent,
        AccountNumberPipe,
        CustomFilterPipe,
        TransferHistoryComponent,
        TransferSendComponent,
        TransferDetailComponent,
        PermanentTransferAddComponent,
        PermanentTransferListComponent,
        DirectDebitAgreementComponent,
        DirectDebitListComponent,
        DirectDebitSendComponent,
        IbanNumberPipe,
        CustomFilterPipe,
        DepositsAddComponent,
        DepositsListComponent,
        DepositTypesListComponent,
        DepositTypesAddComponent,
        CreditsListComponent,
        CreditTypesListComponent,
        CreditTypesAddComponent,
        CreditsAddComponent,
    ],
    providers: [
        AccountService,
        CardService,
        NotificationService,
    ]
})

export class AdminLayoutModule { }
