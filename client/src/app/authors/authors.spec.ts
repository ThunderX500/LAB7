import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Authors } from './authors';
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

describe('Authors', () => {
  let component: Authors;
  let fixture: ComponentFixture<Authors>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    imports: [Authors],
    providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Authors);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the author returned for the requested id', () => {
    component.authorId = 3;
    component.onSubmit();

    const request = httpMock.expectOne('http://localhost:8080/books-api/authors/3');
    expect(request.request.method).toBe('GET');
    request.flush({
      id: 3,
      firstName: 'George',
      lastName: 'Orwell',
      books: [{title: '1984'}, {title: 'Animal Farm'}]
    });

    expect(component.searched).toBeTrue();
    expect(component.selectedAuthor?.lastName).toBe('Orwell');
    expect(component.bookTitles()).toBe('1984, Animal Farm');
  });

  it('should report a not found author when the id is unknown', () => {
    component.authorId = 99999;
    component.onSubmit();

    httpMock.expectOne('http://localhost:8080/books-api/authors/99999')
      .flush(null, {status: 404, statusText: 'Not Found'});

    expect(component.searched).toBeTrue();
    expect(component.selectedAuthor).toBeNull();
  });
});
