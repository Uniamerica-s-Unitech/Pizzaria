import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { EnderecoDetalhesComponent } from './endereco-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('EnderecoDetalhesComponent', () => {
  let component: EnderecoDetalhesComponent;
  let fixture: ComponentFixture<EnderecoDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnderecoDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(EnderecoDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));

  it('should not emit the address when the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'success');
    spyOn(component.retorno, 'emit');
  
    const invalidForm = {
      valid: false
    };
  
    component.salvar(invalidForm);
  
    tick();
  
    expect(component.toastr.success).not.toHaveBeenCalled();
    expect(component.retorno.emit).not.toHaveBeenCalled();
  }));

  it('não deve emitir o evento de retorno ao salvar um formulário que não foi tocado', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    spyOn(component.retorno, 'emit');
  
    const validFormUntouched = {
      valid: true,
      touched: false,
      value: {
        rua: 'Rua A',
        bairro: 'Bairro B',
        numero: 123
      }
    };
  
    component.salvar(validFormUntouched);
  
    tick();
  
    // Ensure that emit is not called
    expect(component.retorno.emit).not.toHaveBeenCalled();
    // Ensure that toastr.error is called
    expect(component.toastr.error).toHaveBeenCalled();
  }));


});