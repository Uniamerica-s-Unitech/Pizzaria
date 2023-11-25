import { TestBed } from '@angular/core/testing';

import { EnderecoService } from './endereco.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('EnderecoService', () => {
  let service: EnderecoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EnderecoService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(EnderecoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
