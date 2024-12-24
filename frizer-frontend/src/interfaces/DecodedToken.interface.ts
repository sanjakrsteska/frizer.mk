export interface DecodedToken {
    id?: number;
    email?: string;
    firstName?: string;
    lastName?: string;
    phoneNumber?: string;
    roles?: string[];
    authorities?: string;
    sub?: string;
    iat?: number;
    exp?: number;
  }