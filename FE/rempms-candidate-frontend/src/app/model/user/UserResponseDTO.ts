import { UserHasApplicationScopeHasUserRoleResponseDTO } from './userHasApplicationScopeHasUserRole/UserHasApplicationScopeHasUserRoleResponseDTO';

export interface UserResponseDTO {
  idUser: number;
  email: string;
  isEmailVerified: boolean;
  firstName: string;
  lastName: string;
  userName: string;
  active: boolean;
  failCount: number;
  accountNonLocked: boolean;
  password: String;
  userHasApplicationScopeHasUserRole: UserHasApplicationScopeHasUserRoleResponseDTO;
}
