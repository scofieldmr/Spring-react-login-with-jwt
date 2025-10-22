import axios from "axios";

const REACT_APP_API_URL = "https://spring-login-backend.onrender.com/api/v1";

export const logUserInApplication = (user) => axios.post(`${REACT_APP_API_URL}/jwtlogin`,user);

