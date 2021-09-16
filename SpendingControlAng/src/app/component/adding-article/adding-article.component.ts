import { Component, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Article } from 'src/app/model/article';
import { DegreeOfUseFullness } from 'src/app/model/enum/degree-of-use-fullness.enum';
import { DailyExpenseService } from 'src/app/service/daily-expense.service';

@Component({
  selector: 'app-adding-article',
  templateUrl: './adding-article.component.html',
  styleUrls: ['./adding-article.component.css'],
})
export class AddingArticleComponent implements OnInit {
  createAnExistingArticle = false;
  everyArticleHasSamePrice = false;
  articleForm: FormGroup;
  article:Article;
  chosenDegOfUseFullNess;
  listDegOfUseFullNess = ["LOW","MEDIUM","HIGH"];
  @Output() dasdasd;

  constructor(
    public activeModal: NgbActiveModal,
    private dailyExpenseService : DailyExpenseService
    ) {}

  ngOnInit(): void {
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

  onSubmit(){
    console.log(this.articleForm.get('qty').valid);
    console.log(this.articleForm.get('price').valid);
    console.log(this.articleForm.valid);

      this.addArticle()
  }

  addArticle(){
    this.article.name = this.articleForm.get('name').value;
    this.article.qty = this.articleForm.get('qty').value;
    this.article.price = this.articleForm.get('price').value;
    this.article.degreeOfUseFullness = this.articleForm.get('degreeOfUseFullness').value;

    this.dailyExpenseService.addArticleToDailyExpense(this.article).subscribe((err) => console.log(err));
  }
}
