import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppleWidget } from './apple-widget';

describe('AppleWidget', () => {
  let component: AppleWidget;
  let fixture: ComponentFixture<AppleWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppleWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppleWidget);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
