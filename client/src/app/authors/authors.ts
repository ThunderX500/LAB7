import {Component, inject, OnDestroy} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Subscription} from "rxjs";
import {AuthorEntity} from '../books/model/bookEntity';
import {BooksService} from '../books/service/books-service';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.html',
  styleUrls: ['./authors.css'],
  imports: [FormsModule]
})
export class Authors implements OnDestroy {
  authorId: number | null = null;
  selectedAuthor: AuthorEntity | null = null;
  searched = false;
  private subscription?: Subscription;
  private booksService: BooksService = inject(BooksService);

  onSubmit(): void {
    if (this.authorId === null || !Number.isInteger(this.authorId) || this.authorId <= 0) {
      return;
    }
    this.subscription?.unsubscribe();
    this.searched = true;
    this.subscription = this.booksService.getAuthor(String(this.authorId)).subscribe({
      next: (data: AuthorEntity) => {
        this.selectedAuthor = data;
      },
      error: (_: any) => {
        this.selectedAuthor = null;
      }
    });
  }

  bookTitles(): string {
    return (this.selectedAuthor?.books ?? []).map(book => book.title).join(', ');
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
