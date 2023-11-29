import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { PedidoDetalhesComponent } from './pedido-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { PedidoService } from 'src/app/services/pedido.service';
import { Pedido } from 'src/app/models/pedido';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}

class PedidoServiceMock {
  save(pedido: any) {
    return of({ mensagem: 'Pedido salvo com sucesso' });
  }
}

describe('PedidoDetalhesComponent', () => {
  let component: PedidoDetalhesComponent;
  let fixture: ComponentFixture<PedidoDetalhesComponent>;

  let pedidoService: PedidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PedidoDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
        { provide: PedidoService, useClass: PedidoServiceMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(PedidoDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  beforeEach(() => {
    pedidoService = TestBed.inject(PedidoService);

    let pedido = new Pedido();
    pedido.valorTotal = 10;
    component.pedido = pedido;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(pedidoService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(pedidoService, 'save').and.callThrough();

    let pedido = new Pedido();
    pedido.valorTotal = 10;
    component.pedido = pedido;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(pedido);
  }));

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));

  it('should save the pedido and display a success message when the form is valid', fakeAsync(() => {
    spyOn(component.toastr, 'success');
    spyOn(component.pedidoService, 'save').and.callThrough();

    component.salvar({ valid: true });

    tick();

    expect(component.toastr.success).toHaveBeenCalledWith('Pedido salvo com sucesso');
    expect(component.pedidoService.save).toHaveBeenCalled();
  }));

  it('should open the modal for adding a product', () => {
    spyOn(component.modalService, 'open').and.callThrough();

    component.adicionarProduto({});

    expect(component.modalService.open).toHaveBeenCalled();
  });

  it('should open the modal for searching clients', () => {
    spyOn(component.modalService, 'open').and.callThrough();

    component.buscar({});

    expect(component.modalService.open).toHaveBeenCalled();
  });
});
