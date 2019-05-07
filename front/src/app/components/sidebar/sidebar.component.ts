import { Component, OnInit } from '@angular/core';

declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Dashboard', icon: 'design_app', class: '' },
    { path: '/clients', title: 'Clients', icon: 'users_single-02', class: '' },
    { path: '/accounts', title: 'Accounts', icon: 'business_money-coins', class: '' },
    { path: '/transfersend', title: 'send transfer', icon: 'business_money-coins', class: '' },
    { path: '/directdebitagreement', title: 'direct debit agreement', icon: 'business_money-coins', class: '' },
    { path: '/directdebitlist', title: 'active direct debits', icon: 'business_money-coins', class: '' },
    { path: '/directdebitsend', title: 'send direct debit', icon: 'business_money-coins', class: '' },
    { path: '/permanentTransferAdd', title: 'set permanent transfer', icon: 'business_money-coins', class: '' },
    { path: '/permanentTransferList', title: 'permanent transfer', icon: 'business_money-coins', class: '' },
];

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
    menuItems: any[];

    constructor() { }

    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);
    }
    isMobileMenu() {
        if (window.innerWidth > 991) {
            return false;
        }
        return true;
    };
}
