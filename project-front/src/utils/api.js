import axios from "axios";

const backUrl = "http://localhost:9070/";

const api = axios.create({
  baseURL: backUrl,
  headers: {
    "Content-Type": "application/json",
  },
});
export default api;
