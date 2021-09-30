import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-disable-account-dialog',
  templateUrl: './disable-account-dialog.component.html',
  styleUrls: ['./disable-account-dialog.component.css']
})
export class DisableAccountDialogComponent implements OnInit {

  @Output() choice:EventEmitter<boolean> = new EventEmitter();

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  sendConfirmation(){
    this.choice.emit(true);
    this.activeModal.close();
  }
}
