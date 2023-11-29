import { TestBed } from '@angular/core/testing';

import { PedidoService } from './pedido.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Pedido } from '../models/pedido';
import { Cliente } from '../models/cliente';
import { Endereco } from '../models/endereco';
import { of } from 'rxjs';
import { Produto } from '../models/produto';
import { Categoria } from '../models/categoria';
import { PedidoProduto } from '../models/pedido-produto';
import { Sabor } from '../models/sabor';

describe('PedidoService', () => {
  let service: PedidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PedidoService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(PedidoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar pedidos abertos', () => {
    const mockPedidos: Pedido[] = [{ id: 1, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0 }];
    spyOn(service.http, 'get').and.returnValue(of(mockPedidos));

    service.listarAbertos().subscribe(pedidos => {
      expect(pedidos).toEqual(mockPedidos);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/abertos`);
  });

  it('listar pedidos finalizados', () => {
    const mockPedidos: Pedido[] = [{ id: 2, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0 }];
    spyOn(service.http, 'get').and.returnValue(of(mockPedidos));

    service.listarFinalizados().subscribe(pedidos => {
      expect(pedidos).toEqual(mockPedidos);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/historico`);
  });

  it('salvar um pedido', () => {
    const mockPedido: Pedido = {id: 1, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0 };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockPedido).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockPedido);
    });
  });

  it('editar um pedido', () => {
    const mockPedido: Pedido = { id: 1, clienteId: {} as Cliente, enderecoId: {} as Endereco, pedidoProdutoList: [] as PedidoProduto[], solicitacao: new Date(), finalizacao: new Date(), valorTotal: 0  };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockPedido).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockPedido.id}`, mockPedido);
    });
  });
});
