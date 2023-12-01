import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ClienteDetalhesComponent } from './cliente-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { Endereco } from 'src/app/models/endereco';
import { ClienteService } from 'src/app/services/cliente.service';
import { Cliente } from 'src/app/models/cliente';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('ClienteDetalhesComponent', () => {
  let component: ClienteDetalhesComponent;
  let fixture: ComponentFixture<ClienteDetalhesComponent>;
  let clienteService: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClienteDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(ClienteDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(() => {
    clienteService = TestBed.inject(ClienteService);

    let cliente = new Cliente();
    cliente.nome = "homam";
    component.cliente = cliente;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(clienteService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(clienteService, 'save').and.callThrough();

    let cliente = new Cliente();
    cliente.nome = "homam";
    component.cliente = cliente;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(cliente);
  }));

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));

  it('should open modal for adding address when cadastrarEndereco is called', () => {
    spyOn(component.modalService, 'open');
    
    component.cadastrarEndereco({});
  
    expect(component.enderecoParaEditar.id).toBe(-1);
    expect(component.modalService.open).toHaveBeenCalled();
  });

  it('should open modal for editing address when editarEndereco is called', () => {
    spyOn(component.modalService, 'open');
    const endereco: Endereco =  { id: 1, rua: 'Rua 1', bairro: 'Bairro 1', numero: 123 };
    const index = 0;
  
    component.editarEndereco({}, endereco, index);
  
    expect(component.enderecoParaEditar).toEqual(endereco);
    expect(component.indiceSelecionadoParaEdicao).toBe(index);
    expect(component.modalService.open).toHaveBeenCalled();
  });

  it('should remove address when deleteEndereco is called', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.cliente.enderecos = [{ id: 1, rua: 'Rua 1', bairro: 'Bairro 1', numero: 123 }];
    const indexToRemove = 0;
  
    component.excluirEndereco(indexToRemove);
  
    expect(window.confirm).toHaveBeenCalled();
    expect(component.cliente.enderecos.length).toBe(0);
  });
});

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ClienteDetalhesComponent } from './cliente-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { Endereco } from 'src/app/models/endereco';
import { ClienteService } from 'src/app/services/cliente.service';
import { Cliente } from 'src/app/models/cliente';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('ClienteDetalhesComponent', () => {
  let component: ClienteDetalhesComponent;
  let fixture: ComponentFixture<ClienteDetalhesComponent>;
  let clienteService: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClienteDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(ClienteDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  beforeEach(() => {
    clienteService = TestBed.inject(ClienteService);

    let cliente = new Cliente();
    cliente.nome = "homam";
    component.cliente = cliente;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(clienteService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(clienteService, 'save').and.callThrough();

    let cliente = new Cliente();
    cliente.nome = "homam";
    component.cliente = cliente;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(cliente);
  }));

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));

  it('should open modal for adding address when cadastrarEndereco is called', () => {
    spyOn(component.modalService, 'open');
    
    component.cadastrarEndereco({});
  
    expect(component.enderecoParaEditar.id).toBe(-1);
    expect(component.modalService.open).toHaveBeenCalled();
  });

  it('should open modal for editing address when editarEndereco is called', () => {
    spyOn(component.modalService, 'open');
    const endereco: Endereco =  { id: 1, rua: 'Rua 1', bairro: 'Bairro 1', numero: 123 };
    const index = 0;
  
    component.editarEndereco({}, endereco, index);
  
    expect(component.enderecoParaEditar).toEqual(endereco);
    expect(component.indiceSelecionadoParaEdicao).toBe(index);
    expect(component.modalService.open).toHaveBeenCalled();
  });

  it('should remove address when deleteEndereco is called', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.cliente.enderecos = [{ id: 1, rua: 'Rua 1', bairro: 'Bairro 1', numero: 123 }];
    const indexToRemove = 0;
  
    component.excluirEndereco(indexToRemove);
  
    expect(window.confirm).toHaveBeenCalled();
    expect(component.cliente.enderecos.length).toBe(0);
  });
});
