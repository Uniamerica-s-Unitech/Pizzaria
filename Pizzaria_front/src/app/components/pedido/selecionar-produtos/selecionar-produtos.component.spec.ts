import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecionarProdutosComponent } from './selecionar-produtos.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('SelecionarProdutosComponent', () => {
  let component: SelecionarProdutosComponent;
  let fixture: ComponentFixture<SelecionarProdutosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelecionarProdutosComponent],
      imports: [HttpClientTestingModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(SelecionarProdutosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
