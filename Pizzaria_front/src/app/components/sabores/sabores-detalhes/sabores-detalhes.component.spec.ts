import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaboresDetalhesComponent } from './sabores-detalhes.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('SaboresDetalhesComponent', () => {
  let component: SaboresDetalhesComponent;
  let fixture: ComponentFixture<SaboresDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaboresDetalhesComponent],
      imports: [HttpClientTestingModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(SaboresDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
