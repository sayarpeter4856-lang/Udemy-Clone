import axios from "axios";
import type {AuthDto} from "../dto/AuthDto.ts";

const AUTH_URL = "/api/auth";

export const loginApiCall = (auth:AuthDto) =>
    axios.post(`${AUTH_URL}/login`, auth);

export const registerApiCall = (auth:AuthDto) =>
    axios.post(`${AUTH_URL}/register`, auth);

export const setToken = (token: string) =>
    localStorage.setItem("token", token);
export const getToken = () =>
    localStorage.getItem("token");

export const setLoggedInUsername = (username:string) =>
    sessionStorage.setItem("loggedInUsername", username);
export const getLoggedInUsername = () =>
    sessionStorage.getItem("loggedInUsername");
export const setRoleName = (roleName:string) =>
    sessionStorage.setItem("roleName", roleName);
export const getRoleName = () =>
    sessionStorage.getItem("roleName");
export const isStudent = () =>
    getRoleName() == "ROLE_STUDENT";
export const isLoggedIn = () =>
    getToken() !== null;
export const  logout = () => {
    localStorage.clear();
    sessionStorage.clear();
}


