import { TestBed } from '@angular/core/testing';

import { ProdutoService } from './produto.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Produto } from '../models/produto';
import { of } from 'rxjs';
import { Categoria } from '../models/categoria';

describe('ProdutoService', () => {
  let service: ProdutoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProdutoService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(ProdutoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar produtos', () => {
    const mockProdutos: Produto[] = [{ id: 1, nome: 'Produto Teste', categoriaId: {} as Categoria, valor: 10, tamanho: 'M', temSabores: false }];
    spyOn(service.http, 'get').and.returnValue(of(mockProdutos));

    service.listar().subscribe(produtos => {
      expect(produtos).toEqual(mockProdutos);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um produto', () => {
    const mockProduto: Produto = {id: 1, nome: 'Novo Produto', categoriaId: {} as Categoria, valor: 15, tamanho: 'M', temSabores: true };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockProduto).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockProduto);
    });
  });

  it('editar um produto', () => {
    const mockProduto: Produto = { id: 1, nome: 'Produto Atualizado', categoriaId: {} as Categoria, valor: 20, tamanho: 'XL', temSabores: true };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockProduto).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockProduto.id}`, mockProduto);
    });
  });

  it('deletar um produto', () => {
    const produtoId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(produtoId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${produtoId}`);
    });
  });
});
