import { Component, OnInit } from '@angular/core';
import { WibbService } from 'src/app/wibb.service';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {
  requests: Request[] = [];

  constructor(private wibbService: WibbService) { }

  ngOnInit(): void {
    this.wibbService.getFeedback().subscribe((requests) => {
      this.requests = (<Request[]>requests).reverse().filter((r) => {
        return r.text && r.text.length > 0;
      });
    });
  }

}
