import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { SaboresListComponent } from './sabores-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

class NgbModalMock {
  open(content: any, options: any): NgbModalRef {
    return { componentInstance: {}, result: Promise.resolve('closed') } as NgbModalRef;
  }
}

describe('SaboresListComponent', () => {
  let component: SaboresListComponent;
  let fixture: ComponentFixture<SaboresListComponent>;
  let modalService: NgbModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaboresListComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [
        { provide: ToastrService, useClass: ToastrServiceMock },
        { provide: NgbModal, useClass: NgbModalMock },
      ],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(SaboresListComponent);
    component = fixture.componentInstance;
    modalService = TestBed.inject(NgbModal);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('should open modal for adding a new sabor', () => {
    spyOn(modalService, 'open').and.callThrough();

    component.cadastrarSabor({});

    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for editing a sabor', () => {
    spyOn(modalService, 'open').and.callThrough();
    const sabor = { id: 1, nome: 'Test Sabor', bloqueado: false, selecionado: false};

    component.editarSabor({}, sabor, 0);

    expect(component.saborParaEditar).toEqual(sabor);
    expect(modalService.open).toHaveBeenCalled();
  });

  it('should open modal for deleting a sabor', () => {
    spyOn(modalService, 'open').and.callThrough();
    const sabor = { id: 1, nome: 'Test Sabor', bloqueado: false, selecionado: false };

    component.excluirSabor({}, sabor, 0);

    expect(component.saborParaExcluir).toEqual(sabor);
    expect(modalService.open).toHaveBeenCalled();
  });

  it('should emit "vincular" event when vincular is called', () => {
    spyOn(component.retorno, 'emit');

    const sabor = { id: 1, nome: 'Test Sabor' , bloqueado: false, selecionado: false};
    component.vincular(sabor);

    expect(component.retorno.emit).toHaveBeenCalledWith(sabor);
  });
});
