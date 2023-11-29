import { TestBed } from '@angular/core/testing';

import { SaborService } from './sabor.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Sabor } from '../models/sabor';
import { of } from 'rxjs';

describe('SaborService', () => {
  let service: SaborService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SaborService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(SaborService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar sabores', () => {
    const mockSabores: Sabor[] = [{ id: 1, nome: 'sabor1', bloqueado: false , selecionado: false}];
    spyOn(service.http, 'get').and.returnValue(of(mockSabores));

    service.listar().subscribe(sabores => {
      expect(sabores).toEqual(mockSabores);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um sabor', () => {
    const mockSabor: Sabor = {id: 1, nome: 'Novo Sabor', bloqueado: false , selecionado: false };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockSabor).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockSabor);
    });
  });

  it('editar um sabor', () => {
    const mockSabor: Sabor = { id: 1, nome: 'Sabor Atualizado', bloqueado: false , selecionado: false };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockSabor).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockSabor.id}`, mockSabor);
    });
  });

  it('deletar um sabor', () => {
    const saborId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(saborId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${saborId}`);
    });
  });
});
