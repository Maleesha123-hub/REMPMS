import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRoleComponent } from './user-role.component';

describe('UserRoleComponent', () => {
  let component: UserRoleComponent;
  let fixture: ComponentFixture<UserRoleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserRoleComponent]
    });
    fixture = TestBed.createComponent(UserRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
