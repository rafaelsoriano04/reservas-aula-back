import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

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
