import { Component, OnInit } from '@angular/core';
import { WibbService } from 'src/app/wibb.service';
import { WibbError } from 'src/app/shared/wibb-error.model';

@Component({
  selector: 'app-reported-errors',
  templateUrl: './reported-errors.component.html',
  styleUrls: ['./reported-errors.component.css']
})
export class ReportedErrorsComponent implements OnInit {
  errors: WibbError[] = [];

  constructor(private wibbService: WibbService) { }

  ngOnInit(): void {
    this.wibbService.getErrors().subscribe((errors) => {
      this.errors = (<WibbError[]> errors).reverse();
    });
  }

}
