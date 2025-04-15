import { UserHasApplicationScopeHasUserRoleRequestDTO } from '../userHasApplicationScopeHasUserRole/UserHasApplicationScopeHasUserRoleRequestDTO';

export interface UserRegisterDTO {
  userId: string;
  userName: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  uuid: string;
  active: Boolean;
  userHasAuthorizeParties: string[];
  userHasApplicationScopeHasUserRoles: UserHasApplicationScopeHasUserRoleRequestDTO[];
}
