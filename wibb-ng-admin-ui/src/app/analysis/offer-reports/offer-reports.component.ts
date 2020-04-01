import { Component, OnInit } from '@angular/core';
import { Report } from 'src/app/shared/report.model';
import { WibbService } from 'src/app/wibb.service';

@Component({
  selector: 'app-offer-reports',
  templateUrl: './offer-reports.component.html',
  styleUrls: ['./offer-reports.component.css']
})
export class OfferReportsComponent implements OnInit {
  reports: Report[] = [];

  constructor(private wibbService: WibbService) { }

  ngOnInit(): void {
    this.wibbService.getReports().subscribe((reports) => {
      this.reports = (<Report[]> reports).reverse();
    });
  }
}
