import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnderecoDetalhesComponent } from './endereco-detalhes.component';

describe('EnderecoDetalhesComponent', () => {
  let component: EnderecoDetalhesComponent;
  let fixture: ComponentFixture<EnderecoDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnderecoDetalhesComponent]
    });
    fixture = TestBed.createComponent(EnderecoDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
