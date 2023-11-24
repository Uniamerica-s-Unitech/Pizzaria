import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricoPedidosComponent } from './historico-pedidos.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('HistoricoPedidosComponent', () => {
  let component: HistoricoPedidosComponent;
  let fixture: ComponentFixture<HistoricoPedidosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoricoPedidosComponent],
      imports: [HttpClientTestingModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(HistoricoPedidosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
