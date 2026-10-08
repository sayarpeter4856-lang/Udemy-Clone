
export interface RegisterDto {
    userType: string;
    username: string;
    password: string;
    email: string;
    studentEducation?: string;
    address?: string;
    education: string;
    skills?: string[];
}