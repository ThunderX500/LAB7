import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.html',
  styleUrls: ['./authors.css'],
  imports: [FormsModule]
})
export class Authors {
  authorId: number | null = null;
  selectedAuthorId: number | null = null;

  onSubmit(): void {
    if (this.authorId === null || !Number.isInteger(this.authorId) || this.authorId <= 0) {
      return;
    }
    this.selectedAuthorId = this.authorId;
    // TODO: Request the author from BooksService and display details or a not-found message.
  }
}
