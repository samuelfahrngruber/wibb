import { Component, OnInit, Input } from '@angular/core';
import { WibbError } from 'src/app/shared/wibb-error.model';

@Component({
  selector: 'app-error-item',
  templateUrl: './error-item.component.html',
  styleUrls: ['./error-item.component.css']
})
export class ErrorItemComponent implements OnInit {
  @Input() error: WibbError;
  @Input() expanded: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleExpanded(){
    this.expanded = !this.expanded;
  }
}
