import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaboresListComponent } from './sabores-list.component';

describe('SaboresListComponent', () => {
  let component: SaboresListComponent;
  let fixture: ComponentFixture<SaboresListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaboresListComponent]
    });
    fixture = TestBed.createComponent(SaboresListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
