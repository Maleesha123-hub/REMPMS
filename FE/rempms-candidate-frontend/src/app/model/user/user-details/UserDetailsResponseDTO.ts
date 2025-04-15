import { UserHasApplicationScopeHasUserRoleResponseDTO } from '../userHasApplicationScopeHasUserRole/UserHasApplicationScopeHasUserRoleResponseDTO';

export interface UserDetailsResponseDTO {
  idUser: number;
  email: string;
  isEmailVerified: boolean;
  firstName: string;
  lastName: string;
  userName: string;
  active: boolean;
  failCount: number;
  userHasApplicationScopeHasUserRole: UserHasApplicationScopeHasUserRoleResponseDTO;
}
