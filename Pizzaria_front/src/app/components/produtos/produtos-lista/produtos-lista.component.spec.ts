import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdutosListaComponent } from './produtos-lista.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Categoria } from 'src/app/models/categoria';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}

describe('ProdutosListaComponent', () => {
  let component: ProdutosListaComponent;
  let fixture: ComponentFixture<ProdutosListaComponent>;
  let modalService: NgbModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProdutosListaComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(ProdutosListaComponent);
    component = fixture.componentInstance;
    modalService = TestBed.inject(NgbModal);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should open modal for adding a new produto', () => {
    spyOn(modalService, 'open').and.callThrough();

    component.cadastrarProduto({});

    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for editing a produto', () => {
    spyOn(modalService, 'open').and.callThrough();
    const produto = { id: 1, nome: 'Test Produto', valor: 10, tamanho: "M", temSabores: false,categoriaId: {} as Categoria };
    component.editarProduto({}, produto, 0);

    expect(component.produtoParaEditar).toEqual(produto);
    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for deleting a produto', () => {
    spyOn(modalService, 'open').and.callThrough();
    const produto = { id: 1, nome: 'Test Produto', valor: 10, tamanho: "M", temSabores: false,categoriaId: {} as Categoria };

    component.excluirProduto({}, produto, 0);

    expect(component.produtoParaExcluir).toEqual(produto);
    expect(modalService.open).toHaveBeenCalled();
  });
});
