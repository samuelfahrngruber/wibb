import { Component, OnInit, Input } from '@angular/core';
import { Report } from 'src/app/shared/report.model';

@Component({
  selector: 'app-offer-report-item',
  templateUrl: './offer-report-item.component.html',
  styleUrls: ['./offer-report-item.component.css']
})
export class OfferReportItemComponent implements OnInit {
  @Input() report: Report;
  @Input() expanded: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleExpanded(){
    this.expanded = !this.expanded;
  }
}
