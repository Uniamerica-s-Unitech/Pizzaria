import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { CategoriaDetalhesComponent } from './categoria-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrService, ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CategoriaService } from 'src/app/services/categoria.service';
import { Categoria } from 'src/app/models/categoria';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('CategoriaDetalhesComponent', () => {
  let component: CategoriaDetalhesComponent;
  let fixture: ComponentFixture<CategoriaDetalhesComponent>;
  let categoriaService: CategoriaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoriaDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(CategoriaDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    categoriaService = TestBed.inject(CategoriaService);

    let categoria = new Categoria();
    categoria.nome = "Pizzas";
    component.categoria = categoria;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(categoriaService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(categoriaService, 'save').and.callThrough();

    let categoria = new Categoria();CategoriaService
    categoria.nome = "Pizzas";
    component.categoria = categoria;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(categoria);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display an error message if the form is invalid', fakeAsync(() => {
    spyOn(component.toastr, 'error');
    
    component.salvar({ valid: false });

    tick();

    expect(component.toastr.error).toHaveBeenCalledWith('Formulário inválido. Preencha os campos corretamente');
  }));
});