import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PedidoListaComponent } from './pedido-lista.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pedido } from 'src/app/models/pedido';
import { Cliente } from 'src/app/models/cliente';
import { Endereco } from 'src/app/models/endereco';
import { PedidoProduto } from 'src/app/models/pedido-produto';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}

describe('PedidoListaComponent', () => {
  let component: PedidoListaComponent;
  let fixture: ComponentFixture<PedidoListaComponent>;
  let modalService: NgbModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PedidoListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(PedidoListaComponent);
    component = fixture.componentInstance;
    modalService = TestBed.inject(NgbModal);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set selected pedido and open details', () => {
    const pedido = { id: 1, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0 }

    component.mostrarDetalhes(pedido);
  
    expect(component.pedidoSelecionado).toEqual(pedido);
  });

  it('should open modal for creating a new pedido', () => {
    spyOn(modalService, 'open').and.callThrough();
  
    component.cadastrarPedido({});
  
    expect(modalService.open).toHaveBeenCalled();
    expect(component.pedidoParaEditar).toEqual(new Pedido());
    expect(component.tituloModal).toBe('Cadastrar Pedido');
  });

  it('should open modal for viewing pedido history', () => {
    spyOn(modalService, 'open').and.callThrough();
  
    component.historico({});
  
    expect(modalService.open).toHaveBeenCalled();
    expect(component.tituloModal).toBe('Histórico Pedido');
  });

});
