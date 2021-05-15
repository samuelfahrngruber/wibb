import { Component, Input, OnInit } from '@angular/core';
import { Store } from 'src/data/types/store';
import { defaultStore } from 'src/data/utils/defaults';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.less'],
})
export class StoreComponent {
  @Input() store: Store;

  constructor() {
    this.store = defaultStore;
  }
}
