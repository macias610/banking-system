import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-accountsingle',
    templateUrl: './accountsingle.component.html',
    styleUrls: ['./accountsingle.component.scss']
})
export class AccountsingleComponent implements OnInit {

    accountId: string;
    constructor(private route: ActivatedRoute) { }

    ngOnInit() {
        this.accountId = this.route.snapshot.paramMap.get('id');

        console.log(this.accountId);
    }

}
