import { TestBed } from '@angular/core/testing';

import { SaborService } from './sabor.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('SaborService', () => {
  let service: SaborService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SaborService],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    service = TestBed.inject(SaborService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
