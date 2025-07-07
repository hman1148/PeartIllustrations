import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthLogo } from './auth-logo';

describe('AuthLogo', () => {
  let component: AuthLogo;
  let fixture: ComponentFixture<AuthLogo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthLogo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthLogo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
