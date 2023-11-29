import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ProdutosDetalhesComponent } from './produtos-detalhes.component';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('ProdutosDetalhesComponent', () => {
  let component: ProdutosDetalhesComponent;
  let fixture: ComponentFixture<ProdutosDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProdutosDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(ProdutosDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));
});
