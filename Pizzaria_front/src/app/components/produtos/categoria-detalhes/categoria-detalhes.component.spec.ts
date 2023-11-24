import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriaDetalhesComponent } from './categoria-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('CategoriaDetalhesComponent', () => {
  let component: CategoriaDetalhesComponent;
  let fixture: ComponentFixture<CategoriaDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoriaDetalhesComponent],
      imports: [HttpClientTestingModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(CategoriaDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
