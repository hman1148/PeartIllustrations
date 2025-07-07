import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoogleWidget } from './google-widget';

describe('GoogleWidget', () => {
  let component: GoogleWidget;
  let fixture: ComponentFixture<GoogleWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GoogleWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoogleWidget);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
