export type EmployeeRole = 'ADMIN' | 'AGENT' | 'SUPERVISOR' | 'VIEWER' | 'EMPLOYEE';

export interface Employee {
  id: string;
  email: string;
  name: string;
  role: EmployeeRole;
  department: string;
  avatarInitials: string;
}
