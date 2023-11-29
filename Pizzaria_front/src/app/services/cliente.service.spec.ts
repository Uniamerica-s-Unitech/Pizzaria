import { TestBed } from '@angular/core/testing';

import { ClienteService } from './cliente.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoginService } from './login.service';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Endereco } from '../models/endereco';
import { Cliente } from '../models/cliente';
import { of } from 'rxjs';

describe('ClienteService', () => {
  let service: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]});
    service = TestBed.inject(ClienteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar produtos', () => {
    const mockClientes: Cliente[] = [{ id: 1, nome: 'Cliente Teste', enderecos: []}];
    spyOn(service.http, 'get').and.returnValue(of(mockClientes));

    service.listar().subscribe(produtos => {
      expect(produtos).toEqual(mockClientes);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um produto', () => {
    const mockCliente: Cliente = {id: 1, nome: 'Novo Cliente', enderecos: []};
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockCliente).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockCliente);
    });
  });

  it('editar um produto', () => {
    const mockCliente: Cliente = { id: 1, nome: 'Cliente Atualizado', enderecos: []};
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockCliente).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockCliente.id}`, mockCliente);
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
