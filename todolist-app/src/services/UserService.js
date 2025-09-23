import axios from "axios";

export const logUserInApplication = (user) => axios.post('http://localhost:8000/api/v1/jwtlogin',user);

