import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [HttpClientTestingModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA
      ]
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set active to 1 when clicking on "Pedidos"', () => {
    component.active = 2; // Set initial active value to something other than 1
    fixture.detectChanges(); // Trigger change detection

    const pedidosButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="pedidos"]');
    pedidosButton.click();

    expect(component.active).toBe(1);
  });

  it('should apply "active" class to "Pedidos" button when active is 1', () => {
    component.active = 1;
    fixture.detectChanges();

    const pedidosButton = fixture.debugElement.nativeElement.querySelector('button[routerLink="pedidos"]');
    expect(pedidosButton.classList.contains('active')).toBeTruthy();
  });
});
