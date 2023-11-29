import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarListComponent } from './cadastrar-list.component';

describe('CadastrarListComponent', () => {
  let component: CadastrarListComponent;
  let fixture: ComponentFixture<CadastrarListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CadastrarListComponent]
    });
    fixture = TestBed.createComponent(CadastrarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
