import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecionarProdutosComponent } from './selecionar-produtos.component';

describe('SelecionarProdutosComponent', () => {
  let component: SelecionarProdutosComponent;
  let fixture: ComponentFixture<SelecionarProdutosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelecionarProdutosComponent]
    });
    fixture = TestBed.createComponent(SelecionarProdutosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
