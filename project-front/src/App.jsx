import SidebarMenu from "./components/menu";
import Login from "./components/login";
import Horarios from './components/horario';
import Reservas from './components/Reservas';
import AuLabs from "./components/AuLabas";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "./App.css";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/menu" element={<SidebarMenu />} />
                <Route path="/horarios" component={Horarios} />
                <Route path="/reservas" component={Reservas} />
                <Route path="/Aulabs" component={AuLabs} />
            </Routes>
        </Router>
    );
}

export default App;
