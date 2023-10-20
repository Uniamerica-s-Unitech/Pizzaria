import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaboresDetalhesComponent } from './sabores-detalhes.component';

describe('SaboresDetalhesComponent', () => {
  let component: SaboresDetalhesComponent;
  let fixture: ComponentFixture<SaboresDetalhesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaboresDetalhesComponent]
    });
    fixture = TestBed.createComponent(SaboresDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
