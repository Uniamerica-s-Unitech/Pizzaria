import { TestBed } from '@angular/core/testing';

import { CategoriaService } from './categoria.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Categoria } from '../models/categoria';
import { of } from 'rxjs';

describe('CategoriaService', () => {
  let service: CategoriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CategoriaService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(CategoriaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('listar categorias', () => {
    const mockCategorias: Categoria[] = [{ id: 1, nome: 'Categoria Teste',produtos:[]}];
    spyOn(service.http, 'get').and.returnValue(of(mockCategorias));

    service.listar().subscribe(categorias => {
      expect(categorias).toEqual(mockCategorias);
    });

    expect(service.http.get).toHaveBeenCalledWith(`${service.API}/lista`);
  });

  it('salvar um categoria', () => {
    const mockCategoria: Categoria = {id: 1, nome: 'Novo Categoria', produtos:[] };
    spyOn(service.http, 'post').and.returnValue(of({}));

    service.save(mockCategoria).subscribe(() => {
      expect(service.http.post).toHaveBeenCalledWith(service.API, mockCategoria);
    });
  });

  it('editar um categoria', () => {
    const mockCategoria: Categoria = { id: 1, nome: 'Categoria Atualizado', produtos:[] };
    spyOn(service.http, 'put').and.returnValue(of({}));

    service.save(mockCategoria).subscribe(() => {
      expect(service.http.put).toHaveBeenCalledWith(`${service.API}/${mockCategoria.id}`, mockCategoria);
    });
  });

  it('deletar um categoria', () => {
    const categoriaId = 1;
    spyOn(service.http, 'delete').and.returnValue(of({}));

    service.deletar(categoriaId).subscribe(() => {
      expect(service.http.delete).toHaveBeenCalledWith(`${service.API}/${categoriaId}`);
    });
  });
});
