import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { HistoricoPedidosComponent } from './historico-pedidos.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { PedidoService } from 'src/app/services/pedido.service';
import { of, throwError } from 'rxjs';
import { Cliente } from 'src/app/models/cliente';
import { Endereco } from 'src/app/models/endereco';
import { PedidoProduto } from 'src/app/models/pedido-produto';

class PedidoServiceMock {
  listarFinalizados() {
    return of([]);
  }
}

describe('HistoricoPedidosComponent', () => {
  let component: HistoricoPedidosComponent;
  let fixture: ComponentFixture<HistoricoPedidosComponent>;
  let pedidoService: PedidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoricoPedidosComponent],
      imports: [HttpClientTestingModule],
      providers: [{ provide: PedidoService, useClass: PedidoServiceMock }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
    });
    fixture = TestBed.createComponent(HistoricoPedidosComponent);
    component = fixture.componentInstance;
    pedidoService = TestBed.inject(PedidoService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update listaPedidosFiltrada with the result of listarFinalizados', fakeAsync(() => {
    const mockPedidos = [
      { id: 1, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0 }
    ];
    spyOn(pedidoService, 'listarFinalizados').and.returnValue(of(mockPedidos));

    component.listarPedidos();
    tick();

    expect(component.listaPedidosOrginal).toEqual(mockPedidos);
    expect(component.listaPedidosFiltrada).toEqual(mockPedidos);
  }));
});
