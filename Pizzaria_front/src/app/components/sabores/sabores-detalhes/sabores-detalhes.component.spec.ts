import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { SaboresDetalhesComponent } from './sabores-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { SaborService } from 'src/app/services/sabor.service';
import { Sabor } from 'src/app/models/sabor';

class ToastrServiceMock {
  success(message?: string, title?: string): void {}
  error(message?: string, title?: string): void {}
}

describe('SaboresDetalhesComponent', () => {
  let component: SaboresDetalhesComponent;
  let fixture: ComponentFixture<SaboresDetalhesComponent>;
  let saborService: SaborService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaboresDetalhesComponent],
      imports: [HttpClientTestingModule, ToastrModule, FormsModule],
      providers: [{ provide: ToastrService, useClass: ToastrServiceMock }],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(SaboresDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    saborService = TestBed.inject(SaborService);

    let sabor = new Sabor();
    sabor.nome = "sabor";
    component.sabor = sabor;
    fixture.detectChanges();
  });

  it('deve chamar o método save ao enviar o formulário', fakeAsync(() => { //colocar o fakeAsync toda vez que rolar coisa assíncrona
    let spy = spyOn(saborService, 'save').and.callThrough();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    form.dispatchEvent(new Event('ngSubmit')); //disparar o mesmo evento que tá configurado na tag

    tick(); //simular uma demora assíncrona
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();
  }));

  it('deve chamar o método save ao enviar o formulário passando objeto', fakeAsync(() => {
    let spy = spyOn(saborService, 'save').and.callThrough();

    let sabor = new Sabor();
    sabor.nome = "sabor";
    component.sabor = sabor;
    fixture.detectChanges();

    let form = fixture.debugElement.nativeElement.querySelector('form');
    console.log(form);
    form.dispatchEvent(new Event('ngSubmit'));

    tick();
    fixture.detectChanges();
    expect(spy).toHaveBeenCalledWith(sabor);
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