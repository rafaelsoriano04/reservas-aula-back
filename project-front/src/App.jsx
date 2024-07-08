import React from "react";
import "bootstrap/dist/js/bootstrap.min.js";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import Menu from "./components/Menu";
import Login from "./components/Login";
import PrivateRoute from "./routes/PrivateRoute";

function App() {
  return (
    <Router>
      <Routes>
        <Route>
          <Route path="/" element={<Navigate to="/login" />} />
        </Route>
        <Route>
          <Route path="/login" element={<Login />} />
        </Route>
        <Route element={<PrivateRoute />}>
          <Route path="/menu" element={<Menu />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
