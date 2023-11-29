import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarDetalhesComponent } from './cadastrar-detalhes.component';

describe('CadastrarDetalhesComponent', () => {
  let component: CadastrarDetalhesComponent;
  let fixture: ComponentFixture<CadastrarDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CadastrarDetalhesComponent]
    });
    fixture = TestBed.createComponent(CadastrarDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
