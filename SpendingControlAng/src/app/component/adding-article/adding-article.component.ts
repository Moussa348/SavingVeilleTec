import { Component, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Article } from 'src/app/model/article';
import { DegreeOfUseFullness } from 'src/app/model/enum/degree-of-use-fullness.enum';
import { DailyExpenseService } from 'src/app/service/daily-expense.service';
import { needZeroAtEnd } from 'src/app/util/formatUtil';

@Component({
  selector: 'app-adding-article',
  templateUrl: './adding-article.component.html',
  styleUrls: ['./adding-article.component.css'],
})
export class AddingArticleComponent implements OnInit {
  listDegOfUseFullNess = ['LOW', 'MEDIUM', 'HIGH'];
  createAnExistingArticle = false;
  everyArticleHasSamePrice = true;
  article: Article = new Article();
  articleForm: FormGroup;
  chosenDegOfUseFullNess;
  @Input() personId;
  disbled = true;
  prices = new Array();
  @Output() dasdasd;

  constructor(
    public activeModal: NgbActiveModal,
    private dailyExpenseService: DailyExpenseService
  ) {}

  ngOnInit(): void {
    console.log(this.personId);
    this.setArticleFormGroup();
  }

  setArticleFormGroup() {
    this.articleForm = new FormGroup({
      name: new FormControl('', Validators.required),
      qty: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
      degreeOfUseFullness: new FormControl('', Validators.required),
    });
  }

  onSubmit() {
    this.addArticle();
  }

  addArticle() {
    this.article.personId = this.personId;
    this.article.name = this.articleForm.get('name').value;
    this.article.qty = this.articleForm.get('qty').value;
    this.article.price = this.articleForm.get('price').value * this.article.qty;
    this.article.degreeOfUseFullness = this.articleForm.get(
      'degreeOfUseFullness'
    ).value;

    //this.dailyExpenseService.addArticleToDailyExpense(this.article).subscribe((err) => console.log(err));
  }

  addPrices(price) {
    this.prices.push.apply(this.prices, price);
    console.log(this.prices);
  }

  setPrices($event) {
    console.log($event.target.value);
  }

  needZeroAtEnd(number) {
    return needZeroAtEnd(number);
  }

  isFieldValid(formControlName) {
    return this.articleForm.get(formControlName).valid;
  }

  isFieldTouched(formControlName) {
    return this.articleForm.get(formControlName).touched;
  }
}
